package com.car.valuation.stepdefinations;

import com.registration.base.WebDriverBase;
import com.registration.pageobject.YourDetailsPage;
import com.registration.pageobject.CarSearchPage;
import com.registration.util.FileReader;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;


/**
 *
 * This class is used to implement steps defined in car valuation feature file.
 */
public class CheckCarRegistrationStepDef extends WebDriverBase {

    private List<String> registrationNumbers;
    private CarSearchPage carSearchpage;
    private YourDetailsPage yourDetailsPage;
    private Map<String, List<String>> listMap;

    @Before
    public void setup() {
        WebDriverBase.initialBrowser();
    }

    @Given("Reads the car input text file (.*)")
    public void readsTheCarInputTextFileINPUT_FILE(String inputFileName) throws IOException {
        registrationNumbers = FileReader.getCarRegistrationNumbers(inputFileName);
    }
    @When("Navigate to website and perform car valuation")
    public void navigateToWebsiteAndPerformCarValuation() {
        listMap = new HashMap<String, List<String>>();
        carSearchpage = new CarSearchPage();
        carSearchpage.clickAcceptLink();
        yourDetailsPage = new YourDetailsPage();
        for (String carRegistrationNumber : registrationNumbers) {
            List<String> list = new ArrayList<String>();
            yourDetailsPage = carSearchpage.sendRegistrationNumAndRandomMileage(carRegistrationNumber);
            if (yourDetailsPage.isRegistrationNumberDisplayed()) {
                list.add(yourDetailsPage.getRegistrationNumber());
                list.add(yourDetailsPage.getMake());
                list.add(yourDetailsPage.getModel());
                list.add(yourDetailsPage.getYear());
                listMap.put(carRegistrationNumber, list);
                carSearchpage = yourDetailsPage.clickBackButtonToSearchPage();
            } else {
                System.out.println("Unable to find Car details :"+carRegistrationNumber);
                carSearchpage = yourDetailsPage.clickBackButtonToSearchPage();
            }
        }
    }

    @Then("Compare the output returned by car valuation website with output text file (.*)")
    public void compareTheOutputReturnedByCarValuationWebsiteWithOutputTextFileOUTPUT_FILE(String outfileName) throws IOException {
        Map<String, List<String>> expectedMap = FileReader.getCarOutputDetails(outfileName);
        for (Map.Entry<String, List<String>> entry : expectedMap.entrySet()) {
            System.out.println("Expected car details : " + entry.getKey() + ", Value : " + entry.getValue());
        }
        for (Map.Entry<String, List<String>> entry : listMap.entrySet()) {
            System.out.println("Actual car details : " + entry.getKey() + ", Value : " + entry.getValue());
        }
        assertEquals("Car input details are not matched with output car details", expectedMap, listMap);
    }

    @After
    public void tearDown() {
        WebDriverBase.close();
    }

}

