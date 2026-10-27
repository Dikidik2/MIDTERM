"""Campus Service Queue Manager (standard library only).

Menu:  python campus_queue_manager.py        Demo: --demo        Tests: --test

Data structure: each ticket is a dict {number, purpose, type, status} kept in ONE list in creation
  order. A dict reads clearly (ticket["status"]) and is easy to print and test; a plain list would
  need magic indexes. Counters are a list of two slots (None = free). Extra status "serving" =
  called to a counter but not yet completed.
Fairness: next_ticket() returns an INDEX into the waiting list, or None if nobody waits. Oldest
  priority goes first, but after 2 priority in a row the oldest regular goes and the streak resets.
  If only one type waits, serve it.
Iterator: LIVE (not a snapshot). It skips tickets no longer waiting; StopIteration ends it.
Estimate: simulates future calls on a COPY of the queue. Result = calls before this ticket
  (1 = next). It counts calls, not time: with both counters busy nobody is called until one frees.
"""
import sys
import unittest

WAITING, SERVING, SERVED, CANCELLED = "waiting", "serving", "served", "cancelled"
REGULAR, PRIORITY = "regular", "priority"
PURPOSES = ("Enrollment", "Records", "Payment")


# ================= QUEUE LOGIC (no print, no input) =================
def clean(purpose, service_type=REGULAR):
    """Validate and normalise one request; raise ValueError if invalid."""
    p = str(purpose).strip().title()
    t = str(service_type).strip().lower()
    if p not in PURPOSES:
        raise ValueError(f"Invalid purpose '{purpose}' (Enrollment, Records, Payment).")
    if t not in (REGULAR, PRIORITY):
        raise ValueError(f"Invalid service type '{service_type}' (regular, priority).")
    return p, t


def next_ticket(waiting, priority_streak):
    """Return the INDEX in `waiting` of the eligible ticket, or None if the list is empty."""
    pri = next((i for i, t in enumerate(waiting) if t["type"] == PRIORITY), None)
    reg = next((i for i, t in enumerate(waiting) if t["type"] == REGULAR), None)
    if pri is None:
        return reg
    if reg is None:
        return pri
    return reg if priority_streak >= 2 else pri


def report(**kwargs):
    """Print optional counts (waiting, served, cancelled, free_counters). Unknown keys are ignored."""
    print("--- Summary report ---")
    for key in ("waiting", "served", "cancelled", "free_counters"):
        if key in kwargs:
            print(f"{key}: {kwargs[key]}")


class WaitingTicketIterator:
    """Live iterator over tickets whose status is still 'waiting'."""

    def __init__(self, tickets):
        self._tickets = tickets
        self._cursor = 0

    def __iter__(self):
        return self

    def __next__(self):
        while self._cursor < len(self._tickets):
            ticket = self._tickets[self._cursor]
            self._cursor += 1
            if ticket["status"] == WAITING:
                return ticket
        raise StopIteration


class QueueManager:
    def __init__(self):
        self.tickets = []
        self.history = []
        self.counters = [None] * 2          # range-style init: None = free
        self.next_number = 1
        self.streak = 0

    def issue_ticket(self, purpose, service_type="regular"):
        p, t = clean(purpose, service_type)  # validate first: no partial ticket
        number = self.next_number
        self.next_number += 1
        self.tickets.append({"number": number, "purpose": p, "type": t, "status": WAITING})
        return number

    def issue_many(self, *requests):
        """Each request is (purpose,) or (purpose, service_type). One bad request creates nothing."""
        cleaned = []
        for i, req in enumerate(requests, 1):
            try:
                cleaned.append(clean(*req))
            except (ValueError, TypeError) as e:
                raise ValueError(f"Request {i} rejected, nothing created: {e}")
        return [self.issue_ticket(p, t) for p, t in cleaned]

    def waiting_tickets(self):
        return [t for t in self.tickets if t["status"] == WAITING]

    def count(self, status):
        return sum(1 for t in self.tickets if t["status"] == status)

    def free_counters(self):
        return self.counters.count(None)

    def find(self, number):
        if not 1 <= number <= len(self.tickets):
            raise ValueError(f"Ticket #{number} does not exist.")
        return self.tickets[number - 1]

    def call_next(self):
        """Return (ticket number, counter number)."""
        if None not in self.counters:
            raise RuntimeError("Both counters are busy. Complete a service first.")
        waiting = self.waiting_tickets()
        idx = next_ticket(waiting, self.streak)
        if idx is None:
            raise RuntimeError("No tickets are waiting.")
        ticket = waiting[idx]
        self.streak = self.streak + 1 if ticket["type"] == PRIORITY else 0
        ticket["status"] = SERVING
        slot = self.counters.index(None)
        self.counters[slot] = ticket["number"]
        return ticket["number"], slot + 1

    def complete(self, number):
        ticket = self.find(number)
        if ticket["status"] != SERVING:
            raise RuntimeError(f"Ticket #{number} cannot be completed: it is {ticket['status']}.")
        slot = self.counters.index(number)
        self.counters[slot] = None
        ticket["status"] = SERVED
        self.history.append(ticket)
        return slot + 1

    def cancel(self, number):
        ticket = self.find(number)
        if ticket["status"] != WAITING:
            raise RuntimeError(f"Ticket #{number} cannot be cancelled: it is {ticket['status']}.")
        ticket["status"] = CANCELLED

    def estimate_position(self, number):
        """Simulate future calls on a copy. Live queue untouched. None if not waiting."""
        copy, streak = list(self.waiting_tickets()), self.streak
        for position in range(1, len(copy) + 1):
            t = copy.pop(next_ticket(copy, streak))
            streak = streak + 1 if t["type"] == PRIORITY else 0
            if t["number"] == number:
                return position
        return None

    def counts(self):
        return dict(waiting=self.count(WAITING), served=self.count(SERVED),
                    cancelled=self.count(CANCELLED), free_counters=self.free_counters())

    def __iter__(self):
        return WaitingTicketIterator(self.tickets)


def fmt(t):
    return f"#{t['number']} {t['type']:<8} {t['purpose']:<10} [{t['status']}]"


def show_waiting(q):
    waiting = q.waiting_tickets()
    if not waiting:
        print("No tickets are waiting.")
    for i in range(len(waiting)):
        t = waiting[i]
        print(f"{i + 1}. {fmt(t)}  est. call position: {q.estimate_position(t['number'])}")
    print("(Position = future calls, not minutes; busy counters delay every call.)")


def show_counters(q):
    for i in range(len(q.counters)):
        n = q.counters[i]
        print(f"Counter {i + 1}: {'free' if n is None else f'busy with #{n}'}")
    print("Completed history:", "(none)" if not q.history else "")
    for t in q.history:
        print("  " + fmt(t))


def run_menu():
    q = QueueManager()
    while True:
        print("\n1 Issue a ticket\n2 Call the next ticket\n3 Complete a service\n4 Cancel a waiting ticket\n"
              "5 Show waiting tickets\n6 Show counter status and completed history\n"
              "7 Show a summary report\n8 Exit")
        choice = input("Choose 1-8: ").strip()
        try:
            if choice == "1":
                purpose = input("Purpose (Enrollment/Records/Payment): ")
                kind = input("Type (regular/priority) [regular]: ").strip()
                n = q.issue_ticket(purpose, kind) if kind else q.issue_ticket(purpose)
                print(f"Ticket #{n} issued.")
            elif choice == "2":
                n, c = q.call_next()
                print(f"Ticket #{n} called to counter {c}.")
            elif choice in ("3", "4"):
                text = input("Ticket number: ").strip()
                if not text.isdigit():
                    print("Ticket number must be digits only.")
                    continue
                n = int(text)
                if choice == "3":
                    print(f"Ticket #{n} done. Counter {q.complete(n)} is free.")
                else:
                    q.cancel(n)
                    print(f"Ticket #{n} cancelled.")
            elif choice == "5":
                show_waiting(q)
            elif choice == "6":
                show_counters(q)
            elif choice == "7":
                report(**q.counts())
            elif choice == "8":
                print("Goodbye!")
                return
            else:
                print(f"Invalid choice '{choice}'. Enter 1 to 8.")
        except (ValueError, RuntimeError) as e:
            print("Error:", e)

def attempt(label, fn, *args):
    try:
        fn(*args)
    except (ValueError, RuntimeError, StopIteration) as e:
        print(f"{label} -> {str(e) or 'StopIteration: no more waiting tickets'}")


def run_demo():
    q = QueueManager()
    print("## Issue 6 tickets + invalid input")
    q.issue_ticket("Enrollment")
    q.issue_ticket("Records", "priority")
    q.issue_ticket("Payment", "priority")
    q.issue_ticket("Enrollment", "priority")
    q.issue_ticket("Records")
    q.issue_ticket("Payment", "regular")
    show_waiting(q)
    attempt("bad purpose", q.issue_ticket, "Library")
    attempt("bad type", q.issue_ticket, "Records", "vip")
    attempt("issue_many with a bad request", q.issue_many, ("Records", "priority"), ("Parking",))
    print("Waiting tickets still:", q.count(WAITING))

    print("\n## Two counters, third call while busy")
    for _ in range(2):
        print("#%d -> counter %d" % q.call_next())
    attempt("third call", q.call_next)

    print("\n## Complete one, then fairness: regular after two priority")
    print("Counter", q.complete(2), "free")
    n, c = q.call_next()
    print(f"#{n} ({q.find(n)['type']}) -> counter {c} (priority #4 was still waiting)")

    print("\n## Cancel #5, iterate to exhaustion, next() once more")
    q.cancel(5)
    it = iter(q)
    for t in it:
        print("iterated", fmt(t))
    attempt("next()", next, it)

    print("\n## Errors and skip of cancelled ticket")
    q.complete(3)
    print("#%d -> counter %d" % q.call_next())
    q.complete(1)
    print("#%d -> counter %d (cancelled #5 skipped)" % q.call_next())
    attempt("complete #99", q.complete, 99)
    attempt("complete #2 again", q.complete, 2)
    attempt("cancel #6 (being served)", q.cancel, 6)
    report(**q.counts(), weather="sunny")
    show_counters(q)

def call_and_complete(q):
    n, _ = q.call_next()
    q.complete(n)
    return n


class QueueTests(unittest.TestCase):
    def test_numbering_never_reused(self):
        q = QueueManager()
        q.issue_ticket("Records"); q.issue_ticket("Payment"); q.cancel(2)
        self.assertEqual(q.issue_ticket("Records"), 3)

    def test_fairness_regular_after_two_priority(self):
        q = QueueManager()
        q.issue_ticket("Enrollment")
        for p in ("Records", "Payment", "Enrollment"):
            q.issue_ticket(p, "priority")
        self.assertEqual([call_and_complete(q) for _ in range(4)], [2, 3, 1, 4])
        self.assertIsNone(next_ticket([], 0))

    def test_both_counters_busy(self):
        q = QueueManager()
        for _ in range(3):
            q.issue_ticket("Records")
        q.call_next(); q.call_next()
        with self.assertRaises(RuntimeError):
            q.call_next()
        q.complete(1)
        self.assertEqual(q.call_next()[0], 3)

    def test_cancelled_ticket_skipped(self):
        q = QueueManager()
        for p in ("Records", "Payment", "Enrollment"):
            q.issue_ticket(p)
        q.cancel(2)
        self.assertEqual([call_and_complete(q), call_and_complete(q)], [1, 3])
        with self.assertRaises(RuntimeError):
            q.cancel(2)
        with self.assertRaises(ValueError):
            q.cancel(9)

    def test_iterator_exhaustion(self):
        q = QueueManager()
        for p in ("Records", "Payment", "Enrollment"):
            q.issue_ticket(p)
        q.cancel(2)
        it = iter(q)
        self.assertEqual([t["number"] for t in it], [1, 3])
        with self.assertRaises(StopIteration):
            next(it)

    def test_estimate_and_issue_many(self):
        q = QueueManager()
        q.issue_ticket("Enrollment")
        for p in ("Records", "Payment", "Enrollment"):
            q.issue_ticket(p, "priority")
        self.assertEqual(q.estimate_position(1), 3)
        self.assertEqual((len(q.waiting_tickets()), q.streak), (4, 0))      
        with self.assertRaises(ValueError):
            q.issue_many(("Records",), ("Nope",))
        self.assertEqual(len(q.tickets), 4)                              

if __name__ == "__main__":
    if "--test" in sys.argv:
        unittest.main(argv=[sys.argv[0], "-v"])
    elif "--demo" in sys.argv:
        run_demo()
    else:
        run_menu()