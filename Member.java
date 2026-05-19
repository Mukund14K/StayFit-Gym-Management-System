// Base class demonstrating Encapsulation & Unit-1 Core Concepts
class Member {
    protected String id;
    protected String name;
    protected double baseFee = 1000.0; // Standard monthly charge

    // Constructor utilizing 'this' keyword
    public Member(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Method to be overridden (Polymorphism)
    public double calculateFee() {
        return baseFee;
    }

    // Getters for displaying data inside the GUI JTable
    public String getId() { return id; }
    public String getName() { return name; }
    public String getType() { return "Regular"; }
}

// Subclass demonstrating Unit-2 Inheritance
class PremiumMember extends Member {
    private boolean personalTrainer;

    // Constructor utilizing 'super' keyword to invoke parent constructor
    public PremiumMember(String id, String name, boolean personalTrainer) {
        super(id, name);
        this.personalTrainer = personalTrainer;
    }

    // Runtime Polymorphism: Overriding parent method to calculate dynamic fee
    @Override
    public double calculateFee() {
        if (personalTrainer) {
            return baseFee + 500.0; // Extra charge for personal trainer
        } else {
            return baseFee + 200.0; // Standard premium facility additions
        }
    }

    @Override
    public String getType() {
        return personalTrainer ? "Premium (+Trainer)" : "Premium";
    }
}