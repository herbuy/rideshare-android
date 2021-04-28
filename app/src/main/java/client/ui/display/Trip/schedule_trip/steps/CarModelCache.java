package client.ui.display.Trip.schedule_trip.steps;

import java.util.HashSet;
import java.util.Set;

import cache.AppDatabase;
import client.ui.display.Trip.schedule_trip.p20210303.dataform.CarModel;

public class CarModelCache extends AppDatabase<CarModel> {
    @Override
    protected Class<CarModel> getClassOfT() {
        return CarModel.class;
    }

    @Override
    protected String getTableName() {
        return "car_model_cache";
    }

    //this function helps us reduce duplicates
    //by replacing items that have at least 80% match
    //with the new value


    public CarModel save(CarModel value) {
        //we delete similar values to reduce duplication
        String keyToDelete = keyToReplace(value.model);
        this.deleteByKey(keyToDelete);
        //then save the new value
        return super.save(value.model, value);
    }

    public String keyToReplace(final String model2){
        CarModel existingCarModel = this.selectFirst(new Where<CarModel>() {
            @Override
            public boolean isTrue(CarModel object) {
                float tanimoto = tanimoto(object.model,model2);
                return tanimoto >= 0.8f;
            }

            private float tanimoto(String value1, String value2) {

                Set<Character> commonCharacters = new HashSet<>();
                Set<Character> unionSet = new HashSet<>();

                for(Character c : value1.toCharArray()){
                    unionSet.add(c);
                    if(value2.contains(c.toString())){
                        commonCharacters.add(c);
                    }
                }
                for(Character c : value2.toCharArray()){
                    unionSet.add(c);
                }
                return unionSet.size() < 1 ? 0 : (float)(commonCharacters.size()) / unionSet.size();
            }
        });

        if(existingCarModel == null){
            return model2;
        }
        else{
            return existingCarModel.model;
        }

    }

}
