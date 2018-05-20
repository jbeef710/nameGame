package com.willowtreeapps;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Random;

/**
 * Created on 5/23/17.
 */
public class HomePage extends BasePage {


    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void pickCorrectPic(List<WebElement> array, String name) {
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i).getText().equals(name)) {
                driver.findElements(By.className("photo")).get(i).click();
            }
        }
    }

    public void validateTitleIsPresent() {
        WebElement title = driver.findElement(By.cssSelector("h1"));
        Assert.assertTrue(title != null);
    }

    public void validateClickingFirstPhotoIncreasesTriesCounter() {
        //Wait for page to load
        sleep(6000);

        int count = Integer.parseInt(driver.findElement(By.className("attempts")).getText());

        driver.findElement(By.className("photo")).click();

        sleep(6000);

        int countAfter = Integer.parseInt(driver.findElement(By.className("attempts")).getText());

        Assert.assertEquals(countAfter, count + 1);
    }

    public void validateClickingCorrectPhotoIncreasesStreakCounter() {
        //Wait for page to load
        sleep(6000);

        int count = Integer.parseInt(driver.findElement(By.className("streak")).getText());
        String whoItBe = driver.findElement(By.id("name")).getText();
        List<WebElement> pics = driver.findElements(By.className("name"));

        pickCorrectPic(pics, whoItBe);

        sleep(6000);

        int countAfter = Integer.parseInt(driver.findElement(By.className("streak")).getText());

        Assert.assertEquals(countAfter, count + 1);
    }

    public void validateWrongPicClickStreakReset() {
        //Wait for page to load
        sleep(6000);

        String whoItBe = driver.findElement(By.id("name")).getText();
        List<WebElement> pics = driver.findElements(By.className("name"));

        for (int i = 0; i < pics.size(); i++) {
            if (!pics.get(i).getText().equals(whoItBe)) {
                driver.findElements(By.className("photo")).get(i).click();
                break;
            }
        }

        sleep(6000);

        int tryCount = Integer.parseInt(driver.findElement(By.className("attempts")).getText());
        int streakCount = Integer.parseInt(driver.findElement(By.className("streak")).getText());

        Assert.assertEquals(tryCount, 2);
        Assert.assertEquals(streakCount, 0);
    }

    public void validateCountsAfterTenRandomPicks() {
        Random random = new Random();

        //Wait for page to load
        sleep(6000);

        int correctCounter = 0;
        int streakCounter = 0;
        int tries = 10;

        for (int i = 0; i < tries; i++) {
            String whoItBe = driver.findElement(By.id("name")).getText();
            List<WebElement> availablePics = driver.findElements(By.cssSelector("div[class*=photo]:not([class*='wrong']"));

            int picToClick = random.nextInt(availablePics.size());
            if (availablePics.get(picToClick).getText().contains(whoItBe)) {
                correctCounter++;
                streakCounter++;
            } else {
                streakCounter = 0;
            }
            driver.findElements(By.cssSelector("div[class*=photo]:not([class*='wrong']")).get(picToClick).click();
            sleep(6000);
        }

        int tryCount = Integer.parseInt(driver.findElement(By.className("attempts")).getText());
        int correctCount = Integer.parseInt(driver.findElement(By.className("correct")).getText());
        int streakCount = Integer.parseInt(driver.findElement(By.className("streak")).getText());

        Assert.assertEquals(tryCount, tries);
        Assert.assertEquals(correctCount, correctCounter);
        Assert.assertEquals(streakCount, streakCounter);
    }

    public void validateNameAndPicsChange() {
        //Wait for page to load
        sleep(6000);

        String whoItBe = driver.findElement(By.id("name")).getText();
        List<WebElement> pics = driver.findElements(By.className("name"));

        pickCorrectPic(pics, whoItBe);

        sleep(6000);

        String whoItBeAfter = driver.findElement(By.id("name")).getText();
        List<WebElement> picsAfter = driver.findElements(By.className("name"));

        int tryCount = Integer.parseInt(driver.findElement(By.className("attempts")).getText());

        Assert.assertEquals(tryCount, 1);
        Assert.assertNotEquals(whoItBe, whoItBeAfter);
        Assert.assertNotEquals(pics, picsAfter);
    }
}
