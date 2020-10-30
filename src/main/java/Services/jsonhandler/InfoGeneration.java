package Services.jsonhandler;

import com.google.gson.Gson;

import java.io.*;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class InfoGeneration {

        Names femaleFirstName;
        Names maleFirstName;
        Names lastName;
        LocationCollection local;

        public InfoGeneration() {
            try {
                setFemaleFirstNames();
                setMaleFirstNames();
                setLastNames();
                setLocations();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    public Names getFemaleFirstName() {
        return femaleFirstName;
    }

    public Names getMaleFirstName() {
        return maleFirstName;
    }

    public Names getLastName() {
        return lastName;
    }

    public LocationCollection getLocal() {
        return local;
    }

    public void setFemaleFirstNames() throws FileNotFoundException {
            Gson gson = new Gson();
            femaleFirstName = gson.fromJson(new FileReader("json/fnames.json"), Names.class);
        }

        public void setMaleFirstNames() throws FileNotFoundException {
            Gson gson = new Gson();
            maleFirstName = gson.fromJson(new FileReader("json/mnames.json"), Names.class);
        }

        public void setLastNames() throws FileNotFoundException {
            Gson gson = new Gson();
            lastName = gson.fromJson(new FileReader("json/snames.json"), Names.class);
        }

        public void setLocations() throws IOException {
            Gson gson = new Gson();
            local = gson.fromJson(new FileReader("json/locations.json"), LocationCollection.class);
        }

        public Location getRandomLocation() {
            Random random = new Random();
            int index = random.nextInt(local.getData().size() - 0);
            return local.getData().get(index);
        }

        public String getRandomFemaleFirstName() {
            Random random = new Random();
            int index = random.nextInt(femaleFirstName.data.size() - 0);
            return femaleFirstName.data.get(index);
        }

        public String getRandomMaleFirstName() {
            Random random = new Random();
            int index = random.nextInt(maleFirstName.data.size() - 0);
            return maleFirstName.data.get(index);
        }
        public String getRandomLastName() {
            Random random = new Random();
            int index = random.nextInt(lastName.data.size() - 0);
            return lastName.data.get(index);
        }

    public String assignRandomID() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }

}
