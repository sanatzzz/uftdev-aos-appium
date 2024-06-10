package net.mf;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.junit.*;
import org.openqa.selenium.WebElement;

import java.net.URI;
import java.net.URL;
import java.time.Duration;
import com.hp.lft.sdk.ModifiableSDKConfiguration;
import com.hp.lft.sdk.SDK;

public class AppiumTest {
    private AndroidDriver driver;
    //Update the below variable to indicate which Appium version to use
    private boolean useAppiumV2 = true;
    private String SERVER = "https://eur.ftdigitallab.valueedge.saas.microfocus.com";

    @Before
    public void setUp() throws Exception {
    	
        UiAutomator2Options caps = new UiAutomator2Options();
        caps.setCapability("platformName", "Android");
        caps.setCapability("udid", "R58N93N4XSD");
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("appPackage", "com.Advantage.aShopping");
        caps.setCapability("appActivity", "com.Advantage.aShopping.SplashActivity");
        caps.setCapability("uftm:oauthClientId", "oauth2-S28k0RJo6z5JpSH8uwEI@microfocus.com");
        caps.setCapability("uftm:oauthClientSecret", "jK1gKgkSFV1CaKAAe7sS");
        caps.setCapability("uftm:tenantId", "393381888");


        //UFTM will default to Appium v1.x if appiumVersion capability is not indicated, so the "else" part is not needed
        if(useAppiumV2)
            caps.setCapability("uftm:appiumVersion", "v2.x");
        else
            caps.setCapability("uftm:appiumVersion", "v1.x");

        driver = new AndroidDriver(new URL(SERVER + "/wd/hub"), caps);
        System.out.println("UFTM session was successfully created [Android device]");
    }

    @After
    public void teardown(){
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testAOSapp() throws Exception{
    	
     	ModifiableSDKConfiguration config = new ModifiableSDKConfiguration();
    	config.setServerAddress(new URI("ws://localhost:5095"));
    	SDK.init(config);
    	
        //Implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().textContains(\"LAPTOPS\")")).click();
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().textContains(\"HP ENVY X360\")")).click();
        driver.findElement(AppiumBy.id("linearLayoutProductDetails")).click();
        driver.findElement(AppiumBy.id("imageViewProductDetailsClose")).click();
        driver.findElement(AppiumBy.id("linearLayoutProductQuantity")).click();
        driver.findElement(AppiumBy.xpath(
                "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.LinearLayout[2]/android.widget.ImageView[2]"))
                .click();
        driver.findElement(AppiumBy.id("textViewApply")).click();
        driver.findElement(AppiumBy.id("buttonProductAddToCart")).click();
        WebElement username = driver.findElement(AppiumBy.xpath("//*[@resource-id='com.Advantage.aShopping:id/AosEditTextLoginUserName']/android.widget.EditText[1]"));
        username.click();
        username.sendKeys("Username");
        WebElement password = driver.findElement(AppiumBy.xpath("//*[@resource-id='com.Advantage.aShopping:id/AosEditTextLoginPassword']/android.widget.EditText[1]"));
        password.click();
        password.sendKeys("password");
        driver.findElement(AppiumBy.id("buttonLogin")).click();
        String ErrorMessage = driver.findElement(AppiumBy.id("textViewFailed")).getText();
        assert ErrorMessage.contains("Incorrect user");
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        driver.findElement(AppiumBy.id("imageViewMenu")).click();
        driver.findElement(AppiumBy.id("textViewMenuHome")).click();
        //printAppiumVersionFromLog();
    }
}

