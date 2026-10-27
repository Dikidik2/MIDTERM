import java.util.*;

public class CampusQueueManager {

    static final String WAITING = "waiting", SERVING = "serving", SERVED = "served", CANCELLED = "cancelled";
    static final String REGULAR = "regular", PRIORITY = "priority";
    static final Set<String> PURPOSES = Set.of("enrollment", "records", "payment");

    static class Ticket {
        final int number; final String purpose, type; String status = WAITING;
        Ticket(int number, String purpose, String type) { this.number = number; this.purpose = purpose; this.type = type; }
        public String toString() { return String.format("#%d %-8s %-10s [%s]", number, type, purpose, status); }
    }

    static class WaitingTicketIterator implements Iterator<Ticket>, Iterable<Ticket> {
        private final List<Ticket> src; private int cursor = 0;
        WaitingTicketIterator(List<Ticket> src) { this.src = src; }
        public Iterator<Ticket> iterator() { return this; }                   
        public boolean hasNext() {
            while (cursor < src.size() && !WAITING.equals(src.get(cursor).status)) cursor++;
            return cursor < src.size();
        }
        public Ticket next() {                                                
            if (!hasNext()) throw new NoSuchElementException("StopIteration: no more waiting tickets");
            return src.get(cursor++);
        }
    }

    final List<Ticket> tickets = new ArrayList<>(), history = new ArrayList<>();
    final Integer[] counters = new Integer[2];    
    int nextNumber = 1, streak = 0;

    static String[] clean(String purpose, String type) {
        String p = purpose == null ? "" : purpose.trim().toLowerCase(), t = type == null ? "" : type.trim().toLowerCase();
        if (!PURPOSES.contains(p)) throw new IllegalArgumentException("Invalid purpose '" + purpose + "' (Enrollment, Records, Payment).");
        if (!t.equals(REGULAR) && !t.equals(PRIORITY)) throw new IllegalArgumentException("Invalid service type '" + type + "' (regular, priority).");
        return new String[]{Character.toUpperCase(p.charAt(0)) + p.substring(1), t};
    }

    int issueTicket(String purpose) { return issueTicket(purpose, REGULAR); }   

    int issueTicket(String purpose, String type) {
        String[] c = clean(purpose, type);         
        tickets.add(new Ticket(nextNumber, c[0], c[1]));
        return nextNumber++;
    }

    List<Integer> issueMany(String[]... requests) {
        List<String[]> ok = new ArrayList<>();
        for (int i = 0; i < requests.length; i++) {
            String[] r = requests[i];
            if (r == null || r.length < 1 || r.length > 2) throw new IllegalArgumentException("Request " + (i + 1) + " is malformed.");
            try { ok.add(clean(r[0], r.length == 2 ? r[1] : REGULAR)); }
            catch (IllegalArgumentException e) { throw new IllegalArgumentException("Request " + (i + 1) + " rejected, nothing created: " + e.getMessage()); }
        }
        List<Integer> nums = new ArrayList<>();
        for (String[] c : ok) nums.add(issueTicket(c[0], c[1]));
        return nums;
    }


    static int nextTicket(List<Ticket> waiting, int priorityStreak) {
        int pri = -1, reg = -1;
        for (int i = 0; i < waiting.size() && (pri < 0 || reg < 0); i++) {
            if (waiting.get(i).type.equals(PRIORITY) && pri < 0) pri = i;
            if (waiting.get(i).type.equals(REGULAR) && reg < 0) reg = i;
        }
        if (pri < 0) return reg;
        if (reg < 0) return pri;
        return priorityStreak >= 2 ? reg : pri;
    }

    List<Ticket> waitingTickets() {
        List<Ticket> w = new ArrayList<>();
        for (Ticket t : tickets) if (WAITING.equals(t.status)) w.add(t);
        return w;
    }

    WaitingTicketIterator waitingIterator() { return new WaitingTicketIterator(tickets); }

    int count(String status) { int n = 0; for (Ticket t : tickets) if (t.status.equals(status)) n++; return n; }

    int freeCounters() { int n = 0; for (Integer c : counters) if (c == null) n++; return n; }

    Ticket find(int number) {
        if (number < 1 || number > tickets.size()) throw new IllegalArgumentException("Ticket #" + number + " does not exist.");
        return tickets.get(number - 1);
    }

    int[] callNext() {
        int free = Arrays.asList(counters).indexOf(null);
        if (free < 0) throw new IllegalStateException("Both counters are busy. Complete a service first.");
        List<Ticket> waiting = waitingTickets();
        int idx = nextTicket(waiting, streak);
        if (idx < 0) throw new IllegalStateException("No tickets are waiting.");
        Ticket t = waiting.get(idx);
        streak = t.type.equals(PRIORITY) ? streak + 1 : 0;
        t.status = SERVING;
        counters[free] = t.number;
        return new int[]{t.number, free + 1};
    }

    int complete(int number) {
        Ticket t = find(number);
        if (!SERVING.equals(t.status)) throw new IllegalStateException("Ticket #" + number + " cannot be completed: it is " + t.status + ".");
        int c = Arrays.asList(counters).indexOf(number);
        counters[c] = null;
        t.status = SERVED;
        history.add(t);
        return c + 1;
    }

    void cancel(int number) {
        Ticket t = find(number);
        if (!WAITING.equals(t.status)) throw new IllegalStateException("Ticket #" + number + " cannot be cancelled: it is " + t.status + ".");
        t.status = CANCELLED;
    }

    int estimatePosition(int number) {
        List<Ticket> copy = waitingTickets();
        int s = streak;
        for (int pos = 1; !copy.isEmpty(); pos++) {
            Ticket t = copy.remove(nextTicket(copy, s));
            s = t.type.equals(PRIORITY) ? s + 1 : 0;
            if (t.number == number) return pos;
        }
        return -1;
    }

    static void report(Map<String, ?> kwargs) {
        System.out.println("--- Summary report ---");
        for (String k : new String[]{"waiting", "served", "cancelled", "free_counters"})
            if (kwargs.containsKey(k)) System.out.println(k + ": " + kwargs.get(k));
    }

    final Scanner in = new Scanner(System.in);

    String ask(String p) { System.out.print(p); return in.nextLine().trim(); }

    void showWaiting() {
        List<Ticket> w = waitingTickets();
        if (w.isEmpty()) System.out.println("No tickets are waiting.");
        for (int i = 0; i < w.size(); i++)
            System.out.println((i + 1) + ". " + w.get(i) + "  est. call position: " + estimatePosition(w.get(i).number));
        System.out.println("(Position = future calls, not minutes; busy counters delay every call.)");
    }

    void showCounters() {
        for (int i = 0; i < counters.length; i++)
            System.out.println("Counter " + (i + 1) + ": " + (counters[i] == null ? "free" : "busy with #" + counters[i]));
        System.out.println("Completed history: " + (history.isEmpty() ? "(none)" : ""));
        for (Ticket t : history) System.out.println("  " + t);
    }

    Map<String, Integer> counts() {
        return Map.of("waiting", count(WAITING), "served", count(SERVED), "cancelled", count(CANCELLED), "free_counters", freeCounters());
    }

    void runMenu() {
        while (true) {
            System.out.println("\n1 Issue a ticket\n2 Call the next ticket\n3 Complete a service\n4 Cancel a waiting ticket\n"
                    + "5 Show waiting tickets\n6 Show counter status and completed history\n7 Show a summary report\n8 Exit");
            String choice = ask("Choose 1-8: ");
            try {
                switch (choice) {
                    case "1" -> {
                        String purpose = ask("Purpose (Enrollment/Records/Payment): "), type = ask("Type (regular/priority) [regular]: ");
                        System.out.println("Ticket #" + (type.isEmpty() ? issueTicket(purpose) : issueTicket(purpose, type)) + " issued.");
                    }
                    case "2" -> { int[] r = callNext(); System.out.println("Ticket #" + r[0] + " called to counter " + r[1] + "."); }
                    case "3", "4" -> {
                        int n;
                        try { n = Integer.parseInt(ask("Ticket number: ")); }
                        catch (NumberFormatException e) { System.out.println("Ticket number must be digits only."); continue; }
                        if (choice.equals("3")) System.out.println("Ticket #" + n + " done. Counter " + complete(n) + " is free.");
                        else { cancel(n); System.out.println("Ticket #" + n + " cancelled."); }
                    }
                    case "5" -> showWaiting();
                    case "6" -> showCounters();
                    case "7" -> report(counts());
                    case "8" -> { System.out.println("Goodbye!"); return; }
                    default -> System.out.println("Invalid choice '" + choice + "'. Enter 1 to 8.");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    static void attempt(String label, Runnable r) {
        try { r.run(); } catch (RuntimeException e) { System.out.println(label + " -> " + e.getMessage()); }
    }

    static void runDemo() {
        CampusQueueManager q = new CampusQueueManager();
        System.out.println("## Issue 6 tickets + invalid input");
        q.issueTicket("Enrollment"); q.issueTicket("Records", "priority"); q.issueTicket("Payment", "priority");
        q.issueTicket("Enrollment", "priority"); q.issueTicket("Records"); q.issueTicket("Payment", "regular");
        q.showWaiting();
        attempt("bad purpose", () -> q.issueTicket("Library"));
        attempt("bad type", () -> q.issueTicket("Records", "vip"));
        attempt("issueMany with a bad request", () -> q.issueMany(new String[]{"Records", "priority"}, new String[]{"Parking"}));
        System.out.println("Waiting tickets still: " + q.count(WAITING));

        System.out.println("\n## Two counters, third call while busy");
        for (int i = 0; i < 2; i++) { int[] r = q.callNext(); System.out.println("#" + r[0] + " -> counter " + r[1]); }
        attempt("third call", q::callNext);

        System.out.println("\n## Complete one, then fairness: regular after two priority");
        System.out.println("Counter " + q.complete(2) + " free");
        int[] r = q.callNext();
        System.out.println("#" + r[0] + " (" + q.find(r[0]).type + ") -> counter " + r[1] + " (priority #4 was still waiting)");

        System.out.println("\n## Cancel #5, iterate to exhaustion, next() once more");
        q.cancel(5);
        WaitingTicketIterator it = q.waitingIterator();
        for (Ticket t : it) System.out.println("iterated " + t);
        attempt("next()", it::next);

        System.out.println("\n## Errors and skip of cancelled ticket");
        q.complete(3);
        r = q.callNext(); System.out.println("#" + r[0] + " -> counter " + r[1]);
        q.complete(1);
        r = q.callNext(); System.out.println("#" + r[0] + " -> counter " + r[1] + " (cancelled #5 skipped)");
        attempt("complete #99", () -> q.complete(99));
        attempt("complete #2 again", () -> q.complete(2));
        attempt("cancel #6 (being served)", () -> q.cancel(6));
        report(Map.of("waiting", q.count(WAITING), "served", q.count(SERVED), "cancelled", q.count(CANCELLED),
                "free_counters", q.freeCounters(), "weather", "sunny"));
        q.showCounters();
    }

    static int failed = 0;

    static void check(String name, boolean ok) { System.out.println((ok ? "PASS " : "FAIL ") + name); if (!ok) failed++; }

    static boolean throwsA(Class<? extends RuntimeException> c, Runnable r) {
        try { r.run(); return false; } catch (RuntimeException e) { return c.isInstance(e); }
    }

    static int callAndComplete(CampusQueueManager q) { int[] r = q.callNext(); q.complete(r[0]); return r[0]; }

    static void runTests() {
        CampusQueueManager a = new CampusQueueManager();             
        a.issueTicket("Records"); a.issueTicket("Payment"); a.cancel(2);
        check("numbering: unique, increasing, never reused", a.issueTicket("Records") == 3);

        CampusQueueManager b = new CampusQueueManager();               
        b.issueTicket("Enrollment"); b.issueTicket("Records", "priority");
        b.issueTicket("Payment", "priority"); b.issueTicket("Enrollment", "priority");
        List<Integer> order = new ArrayList<>();
        for (int i = 0; i < 4; i++) order.add(callAndComplete(b));
        check("fairness: order 2,3,1,4", order.equals(List.of(2, 3, 1, 4)));
        check("nextTicket returns -1 when empty", nextTicket(new ArrayList<>(), 0) == -1);

        CampusQueueManager c = new CampusQueueManager();    
        for (int i = 0; i < 3; i++) c.issueTicket("Records");
        c.callNext(); c.callNext();
        check("both counters busy: third call rejected", throwsA(IllegalStateException.class, c::callNext));
        c.complete(1);
        check("completing frees a counter", c.freeCounters() == 1 && c.callNext()[0] == 3);

        CampusQueueManager d = new CampusQueueManager();    
        d.issueTicket("Records"); d.issueTicket("Payment"); d.issueTicket("Enrollment");
        d.cancel(2);
        check("cancelled ticket is skipped", callAndComplete(d) == 1 && callAndComplete(d) == 3);
        check("cancel twice / nonexistent are errors",
                throwsA(IllegalStateException.class, () -> d.cancel(2)) && throwsA(IllegalArgumentException.class, () -> d.cancel(9)));

        CampusQueueManager e = new CampusQueueManager();               
        e.issueTicket("Records"); e.issueTicket("Payment"); e.issueTicket("Enrollment"); e.cancel(2);
        WaitingTicketIterator it = e.waitingIterator();
        List<Integer> seen = new ArrayList<>();
        for (Ticket t : it) seen.add(t.number);
        check("iterator skips cancelled (1,3)", seen.equals(List.of(1, 3)));
        check("next() after exhaustion throws", throwsA(NoSuchElementException.class, it::next));

        CampusQueueManager f = new CampusQueueManager();               
        f.issueTicket("Enrollment"); f.issueTicket("Records", "priority");
        f.issueTicket("Payment", "priority"); f.issueTicket("Enrollment", "priority");
        check("estimate: #1 is 3rd, queue unchanged", f.estimatePosition(1) == 3 && f.waitingTickets().size() == 4 && f.streak == 0);
        check("issueMany: one bad request creates nothing", throwsA(IllegalArgumentException.class,
                () -> f.issueMany(new String[]{"Records"}, new String[]{"Nope"})) && f.tickets.size() == 4);

        System.out.println(failed == 0 ? "\nAll tests passed." : "\n" + failed + " test(s) failed.");
        if (failed > 0) System.exit(1);
    }

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("--test")) runTests();
        else if (args.length > 0 && args[0].equals("--demo")) runDemo();
        else new CampusQueueManager().runMenu();
    }
}