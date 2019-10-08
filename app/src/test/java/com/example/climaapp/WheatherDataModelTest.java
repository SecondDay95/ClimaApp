package com.example.climaapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class WheatherDataModelTest {

//    WheatherDataModel wheatherDataModel = new WheatherDataModel();
    WheatherController wheatherController = new WheatherController();
    //JSONObject jsonObject = new JSONObject();

//    @Mock
//    JSONObject jsonObject = mock(JSONObject.class);

    @Before
    public void setUp() throws Exception {
//        MockitoAnnotations.initMocks(WheatherDataModelTest.this);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void fromJSON() {
       try {

           JSONObject jsonObject = new JSONObject();
           jsonObject.put("name", "London");
           JSONArray jsonArray = new JSONArray();
           JSONObject item = new JSONObject();
           item.put("id", 800);
           jsonArray.put(0, item);
           jsonObject.put("weather", jsonArray);
           JSONObject jsonObject2 = new JSONObject();
           jsonObject2.put("temp", 289.92);
           //jsonObject1.put("main", jsonObject2);
           jsonObject.put("main", jsonObject2);
           System.out.println("JSON created" + jsonObject.toString());
           System.out.println(jsonObject.getString("name"));
           double temp = jsonObject.getJSONObject("main").getDouble("temp") - 273.15;
           int roundTempValue = (int) Math.rint(temp);
           String tempS = Integer.toString(roundTempValue) + "°";



            WheatherDataModel wheatherDataModel = WheatherDataModel.fromJSON(jsonObject);
           System.out.println(wheatherDataModel.getmCity());
            assertEquals(wheatherDataModel.getmCity(), "London");
            assertThat(wheatherDataModel.getmTemperature(), is(equalTo(tempS)));
            assertThat(wheatherDataModel.getmIconName(), is(equalTo("sunny")));

       }
        catch (JSONException e) {

        }
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put(, )
    }

    @Test
    public void updateWheatherIcon() {
        assertEquals(WheatherDataModel.updateWheatherIcon(200), "tstorm1");
    }
}