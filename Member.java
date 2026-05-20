
class Member {
    protected String id;
    protected String name;
    protected double baseFee = 1000.0; /

    
    public Member(String id, String name) {
        this.id = id;
        this.name = name;
    }

  
    public double calculateFee() {
        return baseFee;
    }

    
    public String getId() { return id; }
    public String getName() { return name; }
    public String getType() { return "Regular"; }
}


class PremiumMember extends Member {
    private boolean personalTrainer;

    
    public PremiumMember(String id, String name, boolean personalTrainer) {
        super(id, name);
        this.personalTrainer = personalTrainer;
    }

    
    @Override
    public double calculateFee() {
        if (personalTrainer) {
            return baseFee + 500.0; 
        } else {
            return baseFee + 200.0; 
        }
    }

    @Override
    public String getType() {
        return personalTrainer ? "Premium (+Trainer)" : "Premium";
    }
}