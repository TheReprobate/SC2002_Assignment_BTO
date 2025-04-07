package btosystem;

import btosystem.classes.*;
import btosystem.classes.enums.*;
import btosystem.controllers.EnquiryController;

public class App {

    public static void main(String[] args) {
        EnquiryController enquiryController = new EnquiryController();

        // === Setup: Create Applicants, Officer, Manager, and Projects ===
        Applicant alice = new Applicant("S1234567A", "Alice", 30, false);
        Applicant bob = new Applicant("S7654321B", "Bob", 35, true);

        HdbOfficer officer = new HdbOfficer("O1111111C", "Officer O", 40, false);
        HdbManager manager = new HdbManager("M2222222D", "Manager M", 50, true);

        ProjectTeam team1 = new ProjectTeam(null);  // temp null project
        Project project1 = new Project("Sunrise Grove", Neighborhood.BEDOK, 0, 9999999, team1, manager);
        team1.setProject(project1);
        team1.getOfficers().add(officer);
        officer.setCurrentTeam(team1);
        manager.getCreatedProjects().add(project1);

        // === Test: Applicant submits enquiry (not yet applied) ===
        Enquiry enquiry1 = enquiryController.createEnquiry(project1, alice, "Is there a playground?");

        // === Test: Applicant applies and submits another enquiry ===
        BtoApplication aliceApp = new BtoApplication(project1, alice);
        alice.setActiveApplication(aliceApp);
        project1.getBtoApplications().add(aliceApp);

        Enquiry enquiry2 = enquiryController.createEnquiry(project1, alice, "When is the result out?");

        // === View all enquiries for Alice ===
        System.out.println("Applicant views her enquiries:");
        for (Enquiry e : alice.getEnquiries()) {
            System.out.println("Content: " + e.getContent());
        }

        // === Edit enquiry before reply ===
        System.out.println("\nEditing first enquiry...");
        enquiryController.editEnquiry(enquiry1, "Is there a swimming pool?");
        System.out.println("Edited content: " + enquiry1.getContent());

        // === Officer replies to enquiry (valid case) ===
        enquiryController.replyEnquiry(officer, enquiry1, "Yes, there is a pool.");
        System.out.println("\nOfficer replied: " + enquiry1.getReply());

        // === Try editing replied enquiry (should fail) ===
        System.out.println("\nAttempting to edit replied enquiry...");
        int editResult = enquiryController.editEnquiry(enquiry1, "Can I book the pool?");
        System.out.println(editResult == 0 ? "Edit failed (replied)." : "Edit succeeded (unexpected).");

        // === Try deleting replied enquiry (should fail) ===
        System.out.println("\nAttempting to delete replied enquiry...");
        int delResult = enquiryController.deleteEnquiry(alice.getEnquiries(), enquiry1);
        System.out.println(delResult == 0 ? "Delete failed (replied)." : "Delete succeeded (unexpected).");

        // === Officer views project enquiries ===
        System.out.println("\nOfficer views project enquiries:");
        for (Enquiry e : project1.getEnquiries()) {
            if (officer.getCurrentTeam().getProject() == e.getProject()) {
                System.out.println("Officer sees: " + e.getContent());
            }
        }

        // === Officer attempts to reply to unassigned project ===
        Project project2 = new Project("Sunset Bay", Neighborhood.JURONG, 0, 9999999, null, null);
        Enquiry fakeEnquiry = enquiryController.createEnquiry(project2, bob, "Is this near MRT?");
        int fakeReply = enquiryController.replyEnquiry(officer, fakeEnquiry, "Yes");
        System.out.println("\nOfficer replies to project not handled: "
                + (fakeReply == 0 ? "Reply failed (not handled)." : "Reply succeeded (unexpected)."));

        // === Manager views enquiries in handled & unhandled projects ===
        System.out.println("\nManager views enquiries in handled project:");
        for (Enquiry e : project1.getEnquiries()) {
            System.out.println("Handled: " + e.getContent());
        }

        System.out.println("Manager views enquiries in unhandled project:");
        for (Enquiry e : project2.getEnquiries()) {
            System.out.println("Unhandled: " + e.getContent());
        }

        // === Manager replies to enquiry in handled project (should succeed) ===
        Enquiry enquiry3 = enquiryController.createEnquiry(project1, bob, "Is parking free?");
        int managerHandledReply = enquiryController.replyEnquiry(manager, enquiry3, "Yes, free parking.");
        System.out.println("\nManager replies to handled project: "
                + (managerHandledReply == 1 ? "Reply succeeded." : "Reply failed (unexpected)."));

        // === Manager replies to enquiry in unhandled project (should fail) ===
        int managerUnhandledReply = enquiryController.replyEnquiry(manager, fakeEnquiry, "Check online.");
        System.out.println("Manager replies to unhandled project: "
                + (managerUnhandledReply == 0 ? "Reply failed (not handled)." : "Reply succeeded (unexpected)."));
    }
}
