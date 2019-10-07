package com.example.climaapp;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WheatherDataModelTest {

    WheatherDataModel wheatherDataModel = new WheatherDataModel();
    WheatherController wheatherController = new WheatherController();

    @Before
    public void setUp() throws Exception {


    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void fromJSON() {
//        try {
////            //JSONArray jsonArray = new JSONArray();
//////            //jsonArray.put("city");
//////            JSONObject jsonObject = mock(JSONObject.class);
//////            //jsonObject.put("id", wheatherController.APP_ID);
//////            jsonObject.put("name", "London");
//////            //jsonObject.put()
//////            WheatherDataModel.fromJSON(jsonObject);
//////            Assert.assertEquals(wheatherDataModel.getmCity(), "London");
//        }
//        catch (JSONException e) {
//
//        }
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put(, )
    }

    @Test
    public void updateWheatherIcon() {
        Assert.assertEquals(WheatherDataModel.updateWheatherIcon(200), "tstorm1");
    }
}