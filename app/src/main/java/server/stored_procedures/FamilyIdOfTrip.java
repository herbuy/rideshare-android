package server.stored_procedures;

public class FamilyIdOfTrip {
    private boolean notFound;
    private String id;

    public FamilyIdOfTrip(String tripId) {
        init(tripId);
    }

    private void init(final String tripId) {
        SPFamilyMember spFamilyMember = new SPFamilyMember(tripId);
        if(!spFamilyMember.isFound()){
            notFound = true;
        }
        else{
            id = spFamilyMember.getFamilyMember().getFamilyId();

        }
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object id2) {
        try{
            FamilyIdOfTrip familyIdOfTrip = (FamilyIdOfTrip)id2;
            return this.id != null
                    && !this.id.trim().equalsIgnoreCase("")
                    && familyIdOfTrip.getId() != null
                    && !familyIdOfTrip.getId().trim().equalsIgnoreCase("")
                    && this.id.trim().equalsIgnoreCase(familyIdOfTrip.getId().trim());
        }
        catch (Throwable t){
            return false;
        }
    }
}
