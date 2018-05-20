package com.willowtreeapps;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class WebTest {

    private WebDriver driver;

    /**
     * Change the prop if you are on Windows or Linux to the corresponding file type
     * The chrome WebDrivers are included on the root of this project, to get the
     * latest versions go to https://sites.google.com/a/chromium.org/chromedriver/downloads
     */
    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        Capabilities capabilities = DesiredCapabilities.chrome();
        driver = new ChromeDriver(capabilities);
        driver.navigate().to("http://www.ericrochester.com/name-game/");
    }

    @Test
    public void test_validate_title_is_present() {
        new HomePage(driver)
                .validateTitleIsPresent();
    }

    @Test
    public void test_clicking_photo_increases_tries_counter() {
        new HomePage(driver)
                .validateClickingFirstPhotoIncreasesTriesCounter();
    }

    @Test
    public void test_clicking_correct_photo_increases_streak_counter() {
        new HomePage(driver)
                .validateClickingCorrectPhotoIncreasesStreakCounter();
    }

    @Test
    public void test_streak_counter_reset_on_incorrect_selection() {
        new HomePage(driver)
                .validateClickingCorrectPhotoIncreasesStreakCounter();
        new HomePage(driver)
                .validateWrongPicClickStreakReset();
    }

    @Test
    public void test_counters_post_10_random_selections() {
        new HomePage(driver)
                .validateCountsAfterTenRandomPicks();
    }

    @Test
    public void test_name_and_pics_change_after_correct_selection() {
        new HomePage(driver)
                .validateNameAndPicsChange();
    }

    @After
    public void teardown() {
        driver.quit();
        System.clearProperty("webdriver.chrome.driver");
    }
}
