import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.fail;


public class JavaTest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    System.setProperty("webdriver.gecko.driver", "C:\\Users\\Alexandra\\Desktop\\AD\\учебка3\\2сем\\ТПО\\ЛР3\\TPO3\\resources\\geckodriver.exe");
    driver = new FirefoxDriver();
    System.setProperty("webdriver.chrome.driver", "C:\\Users\\Alexandra\\Desktop\\AD\\учебка3\\2сем\\ТПО\\ЛР3\\TPO3\\resources\\chromedriver.exe");
    //driver = new ChromeDriver();
    baseUrl = "https://archive.org/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }


  @Test
  public void testLoginPage() throws Exception {
    driver.get(baseUrl + "/");
    driver.findElement(By.xpath("//a[span[contains(@class, \"hidden-xs-span\")]]")).click();
    Assert.assertTrue("No login page", driver.findElements(By.xpath("//div[contains(@class, \"formElement\")]/input[@type=\"email\"]")).size() != 0);
  }

  @Test
  public void testLoginAllNormal() throws Exception {
    driver.get(baseUrl + "/");
    driver.findElement(By.xpath("//a[span[contains(@class, \"hidden-xs-span\")]]")).click();
    driver.findElement(By.xpath("//div[contains(@class, \"formElement\")]/input[@type=\"email\"]")).clear();
    driver.findElement(By.xpath("//div[contains(@class, \"formElement\")]/input[@type=\"email\"]")).sendKeys("orlovadasha@inbox.ru");
    driver.findElement(By.xpath("//div[contains(@class, \"formElement\")]/input[@type=\"password\"]")).clear();
    driver.findElement(By.xpath("//div[contains(@class, \"formElement\")]/input[@type=\"password\"]")).sendKeys("deadcandance");
    driver.findElement(By.xpath("//input[contains(@class, \"btn-primary\") and @type=\"submit\"]")).click();
    Assert.assertEquals("No right user name", "orlovadk", driver.findElement(By.xpath("//a[contains(@class, \"ghost80\")]/span[contains(@class, \"hidden-xs-span\")]")).getText());
  }

  @Test
  public void testLoginMailNormalPassWrong() throws Exception {
    driver.get(baseUrl + "/");
    driver.findElement(By.xpath("//a[span[contains(@class, \"hidden-xs-span\")]]")).click();
    driver.findElement(By.xpath("//div[contains(@class, \"formElement\")]/input[@type=\"email\"]")).clear();
    driver.findElement(By.xpath("//div[contains(@class, \"formElement\")]/input[@type=\"email\"]")).sendKeys("orlovadasha@inbox.ru");
    driver.findElement(By.xpath("//div[contains(@class, \"formElement\")]/input[@type=\"password\"]")).clear();
    driver.findElement(By.xpath("//div[contains(@class, \"formElement\")]/input[@type=\"password\"]")).sendKeys("12345");
    driver.findElement(By.xpath("//input[contains(@class, \"btn-primary\") and @type=\"submit\"]")).click();
    String wrongPasswd = driver.findElement(By.xpath("//div[@class=\"error\"]/a")).getText();
    Assert.assertEquals("Passwd error doen't work", "password reset", wrongPasswd);
  }

  @Test
  public void testLoginMailWrong() throws Exception {
    driver.get(baseUrl + "/");
    driver.findElement(By.xpath("//a[span[contains(@class, \"hidden-xs-span\")]]")).click();
    driver.findElement(By.xpath("//div[contains(@class, \"formElement\")]/input[@type=\"email\"]")).clear();
    driver.findElement(By.xpath("//div[contains(@class, \"formElement\")]/input[@type=\"email\"]")).sendKeys("orlova@inbox.ru");
    driver.findElement(By.xpath("//input[contains(@class, \"btn-primary\") and @type=\"submit\"]")).click();
    String wrongPasswd = driver.findElement(By.xpath("//div[@class=\"error\"]/a")).getText();
    Assert.assertEquals("Passwd error doen't work", "register a new account", wrongPasswd);
  }

 @Test
  public void projectList() throws Exception {
   driver.get(baseUrl + "/");
   Actions action = new Actions(driver);
   action.moveToElement(driver.findElement(By.xpath("//a[contains(@class, \"navbar-brand\")]/span[contains(@class, \"iconochive-logo\")]"))).build().perform();

   WebElement el = driver.findElement(By.xpath("//div[contains(@class, \"navbar-static-top\")]//li/a[text() = \"PROJECTS\"]"));
   ((JavascriptExecutor)driver).executeScript("arguments[0].click();", el);
   String header = driver.findElement(By.xpath("//div[contains(@class, \"col-md-9\")]/h1")).getText();
   Assert.assertEquals("No project was shown", "Internet Archive Projects", header);

 }

  @Test
  public void ImagesList() throws Exception {
    driver.get(baseUrl + "/");
    driver.findElement(By.xpath("//div[@class = \"mt-big\"]//span[@class = \"iconochive-image\"]")).click();
    String header = driver.findElement(By.xpath("//div[contains(@class, \"welcome-left\")]//h1")).getText();
    Assert.assertEquals("Image page wasn't showed", "Images", header);
  }

  @Test
  public void SearchUrlNormalTest() throws Exception {
    driver.get(baseUrl + "/");
    WebElement search =  driver.findElement(By.xpath("//input[contains(@class, \"form-control input-sm roundbox20\") and @name=\"url\"]"));
    search.clear();
    search.sendKeys("some");
    search.sendKeys(Keys.ENTER);
    Integer count = driver.findElements(By.xpath("//div[@class = \"result-item\"]/div[@class = \"result-details\"]//b[text() = \"some\"]")).size();
    Assert.assertTrue("Url search failed", count != 0);
  }

  @Test
  public void SearchUrlNotNormalTest() throws Exception {
    driver.get(baseUrl + "/");
    WebElement search =  driver.findElement(By.xpath("//input[contains(@class, \"form-control input-sm roundbox20\") and @name=\"url\"]"));
    search.clear();
    search.sendKeys("aaaaaabbbbbbb");
    search.sendKeys(Keys.ENTER);
    String result = driver.findElement(By.xpath("//p[@class = \"empty-result\"]")).getText();
    Assert.assertTrue("Url search failed", result.contains("No results found"));
  }

  @Test
  public void UploadNonSignedTest() throws Exception {
    driver.get(baseUrl + "/");
    driver.findElement(By.xpath("//span[@class = \"iconochive-upload\"]")).click();
    String result = driver.findElement(By.xpath("//p/b/a[1]")).getText();
    Assert.assertEquals("No signed in user can't upload!!!!", result, "Log in");
  }

  @Test
  public void UploadSignedTest() throws Exception {
    driver.get(baseUrl + "/");
    driver.findElement(By.xpath("//a[span[contains(@class, \"hidden-xs-span\")]]")).click();
    driver.findElement(By.xpath("//div[contains(@class, \"formElement\")]/input[@type=\"email\"]")).clear();
    driver.findElement(By.xpath("//div[contains(@class, \"formElement\")]/input[@type=\"email\"]")).sendKeys("orlovadasha@inbox.ru");
    driver.findElement(By.xpath("//div[contains(@class, \"formElement\")]/input[@type=\"password\"]")).clear();
    driver.findElement(By.xpath("//div[contains(@class, \"formElement\")]/input[@type=\"password\"]")).sendKeys("deadcandance");
    driver.findElement(By.xpath("//input[contains(@class, \"btn-primary\") and @type=\"submit\"]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("//span[@class = \"iconochive-upload\"]")).click();
    Assert.assertTrue("Signed user can upload!!!", driver.findElements(By.xpath("//a/div[text() = \"Upload Files\"]")).size() != 0);
  }

  @Test
  public void AdvanceSearchNormal() throws Exception{
    driver.get(baseUrl + "/");
      if (driver.getClass() == FirefoxDriver.class)
        driver.findElement(By.xpath("//div[contains(@class, \"form-group\")]//a")).click();
      else {
          String href = driver.findElement(By.xpath("//div[contains(@class, \"form-group\")]//a")).getAttribute("href");
          driver.get(href);
      }
    driver.findElement(By.xpath("//div[contains(@class, \"row\") and position() = 1]/div[contains(@class, \"col-sm-7\")]/input")).sendKeys("Laon");
    driver.findElement(By.xpath("//div[contains(@class, \"row\") and position() = 3]/div[contains(@class, \"col-sm-7\")]/input")).sendKeys("Adeath files");
    driver.findElement(By.xpath("//center/input[contains(@class, \"btn btn-primary\")]")).click();
    Assert.assertTrue("Search failed", driver.findElements(By.xpath("//div[@class=\"ttl\"]")).size() != 0);
  }

  @Test
  public void filterSearch() throws Exception {
    driver.get(baseUrl + "/");
    driver.findElement(By.xpath("//div[@class = \"mt-big\"]//span[@class = \"iconochive-image\"]")).click();
    driver.findElement(By.xpath("//div[@class = \"farow\" and position() = 4]/div[@class = \"facell\"]/input")).click();
    Thread.sleep(1000);
    Assert.assertTrue("Search failed!", driver.findElements(By.xpath("//img[@class = \"item-img \"]")).size() != 0);
  }

  @Test
  public void normalSearch() throws Exception {
    driver.get(baseUrl + "/");
    WebElement el = driver.findElement(By.xpath("//form[label[@class = \"sr-only\" and text() = \"Search the Archive\"]]/input[@type=\"text\"]"));
    el.sendKeys("Hello");
    el.sendKeys(Keys.ENTER);
    Assert.assertTrue("Search failed", driver.findElements(By.xpath("//div[@class = \"ttl\"]")).size() != 0);
  }

  @Test
  public void NewsInfoFromProjects() throws Exception {
    driver.get(baseUrl + "/");
    Actions action = new Actions(driver);
    action.moveToElement(driver.findElement(By.xpath("//a[contains(@class, \"navbar-brand\")]/span[contains(@class, \"iconochive-logo\")]"))).build().perform();

    WebElement el = driver.findElement(By.xpath("//div[contains(@class, \"navbar-static-top\")]//li/a[text() = \"PROJECTS\"]"));
    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
      if (driver.getClass() == FirefoxDriver.class)
          driver.findElement(By.xpath("//div[@class = \"box\"]/h1/a")).click();
      else {
          String href = driver.findElement(By.xpath("//div[@class = \"box\"]/h1/a")).getAttribute("href");
          driver.get(href);
      }
    String result = driver.findElement(By.xpath("//h1")).getText();
    Assert.assertTrue("News page wasn't opened!!!", result.contains("News"));
  }

  @Test
  public void DonateTest() throws Exception {
    driver.get(baseUrl + "/");
    if (driver.getClass() == FirefoxDriver.class) {
        driver.findElement(By.xpath("//li[contains(@class, \"dropdown dropdown-ia pull-right\")]/a[@data-original-title = \"Donate\"]")).click();
        driver.findElement(By.xpath("//a[contains(@class, \"donation-faqs-link\")]")).click();
    }
      else {
        String href = driver.findElement(By.xpath("//li[contains(@class, \"dropdown dropdown-ia pull-right\")]/a[@data-original-title = \"Donate\"]")).getAttribute("href");
        driver.get(href);
        href =  driver.findElement(By.xpath("//a[contains(@class, \"donation-faqs-link\")]")).getAttribute("href");
        driver.get(href);
    }

    Assert.assertTrue("Donate FAQ doesn't exist", driver.findElement(By.xpath("//h1[@class = \"entry-title\"]")).isDisplayed());
  }

  @Test
  public void StatisticTest() throws Exception {
      driver.get(baseUrl + "/");
      Actions action = new Actions(driver);
      action.moveToElement(driver.findElement(By.xpath("//a[contains(@class, \"navbar-brand\")]/span[contains(@class, \"iconochive-logo\")]"))).build().perform();

      WebElement el = driver.findElement(By.xpath("//div[contains(@class, \"navbar-static-top\")]//li/a[text() = \"ABOUT\"]"));
      ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
      driver.findElement(By.xpath("//div[@class = \"c1nav\"]/a[text() = \"Server Statistics\"]")).click();
      String header = driver.findElement(By.xpath("//h2")).getText();
      Assert.assertTrue("Statisctics failed", header.contains("Number"));
  }

    @Test
    public void SoftwareTesting() throws Exception {
        driver.get(baseUrl + "/");
        driver.findElement(By.xpath("//a[contains(@class, \"navia-link software\")]")).click();
        driver.findElement(By.xpath("//div[@class=\"linx\"]/a[text() = \"MS-DOS Games\"]")).click();
        Assert.assertTrue("software collections from upper menu failed", driver.findElement(By.xpath("//div[@class = \"row\"]//h1")).getText().contains("Software Library"));
    }

    @Test
    public void SoftwareElementReviewTesting() throws Exception {
        driver.get(baseUrl + "/");
        driver.findElement(By.xpath("//a[contains(@class, \"navia-link software\")]")).click();
        driver.findElement(By.xpath("//div[@class=\"linx\"]/a[text() = \"MS-DOS Games\"]")).click();
        List<WebElement> elements = driver.findElements(By.xpath("//div[contains(@class, \"item-ttl\")]/a[div[contains(text(), \"Oregon\")]]"));
        if (elements.size() > 0) {
            driver.get(elements.get(0).getAttribute("href"));
            //elements.get(0).click();
        }
        else
            Assert.assertTrue("No such element", false);
        driver.findElement(By.xpath("//div[contains(@class, \"pull-right\")]/a[@class = \"stealth\"]")).click();
        String header = driver.findElement(By.xpath("//div[contains(@class, \"container container-ia\")]/div")).getText();
        Assert.assertTrue("Problems with software element reviewing " + header, driver.findElement(By.xpath("//div[contains(@class, \"container container-ia\")]/div")).isDisplayed());
    }

    @Test
    public void SoftwareElementTesting() throws Exception {
        driver.get(baseUrl + "/");
        driver.findElement(By.xpath("//a[contains(@class, \"navia-link software\")]")).click();
        driver.findElement(By.xpath("//div[@class=\"linx\"]/a[text() = \"MS-DOS Games\"]")).click();
        List<WebElement> elements = driver.findElements(By.xpath("//div[contains(@class, \"item-ttl\")]/a[div[contains(text(), \"Oregon\")]]"));
        if (elements.size() > 0) {
            driver.get(elements.get(0).getAttribute("href"));
            //elements.get(0).click();
        }
        else
            Assert.assertTrue("No such element", false);
        Assert.assertTrue("Element info failed", driver.findElement(By.xpath("//b[text() = \"Published by\"]")).isDisplayed());
    }

    @Test
    public void AddingPostTest() throws Exception {
        driver.get(baseUrl + "/");
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(By.xpath("//a[contains(@class, \"navbar-brand\")]/span[contains(@class, \"iconochive-logo\")]"))).build().perform();

        WebElement el = driver.findElement(By.xpath("//div[contains(@class, \"navbar-static-top\")]//li/a[text() = \"HELP\"]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        driver.findElement(By.xpath("//a[span[contains(text(),\"New Post\")]]")).click();
        //String text = driver.findElement(By.xpath("//div[contains(@class, \"container container-ia\")]//div")).getText();
        Assert.assertTrue("Adding Post failed", driver.findElement(By.xpath("//div[contains(@class, \"container container-ia\")]//div")).isDisplayed());
    }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }



  /*private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }*/
}
