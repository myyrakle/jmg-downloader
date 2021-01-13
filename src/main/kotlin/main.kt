import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.support.ui.WebDriverWait
import java.io.File
import java.lang.StringBuilder
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import java.util.concurrent.TimeUnit

/*val driver = ChromeDriver();

    val js = driver as JavascriptExecutor

    driver.get("https://m.joara.com/book/1502867");
    driver.manage().timeouts().implicitlyWait(2000, TimeUnit.SECONDS);*/

//WebDriverWait::

/*while (footerIsNotPresent()) {
    driver.executeScript("window.scrollTo(0, document.body.scrollHeight);")
}*/

/*for(e in driver.findElementsByTagName("span")) {
    //println(e.text)
}*/

/*val title = driver.findElementsByTagName("span")[1].text

println("제목: $title");

val text = StringBuilder();

val button = driver.findElementByCssSelector(".btn.btn-block.btn-primary")
button.click()
driver.manage().timeouts().implicitlyWait(2000, TimeUnit.SECONDS);*/

fun main() {
    System.setProperty("webdriver.chrome.driver", "./chromedriver.exe");

    //val link = "https://m.joara.com/viewer?cid=Ni+ccy7qQiijC4A060ZEmg==&bookCode=1502867&sortno=1";

    print("1화 링크좀 복붙해보소: ")
    val link = Scanner(System.`in`).nextLine()

    val text = StringBuilder();
    var count = 1;

    val driver = ChromeDriver()
    // 창 최대화
    driver.manage().window().maximize()

    // first loop
    driver.get(link);
    driver.manage().timeouts().implicitlyWait(2000, TimeUnit.SECONDS);

    driver.findElementByCssSelector(".pc-guide-action > a").click()
    //driver.findElementByCssSelector(".action-bar > a").click()

    Thread.sleep(5000);

    val ps = driver.findElementsByTagName("p")
    for(e in ps) {
        text.append(e.text)
        text.append("\n");
    }

    // 다음화 버튼 뜨게 클릭
    /*Thread.sleep(5000);

    try {
        driver.findElementsByCssSelector(".view-arrow")
    } catch(e: Exception) {
        driver.findElementByCssSelector(".viewScroll-Center").click();
    }*/


    var title = ""

    driver.findElementByCssSelector(".viewScroll-Center").click();
    if(title=="") {
        title = driver.findElementByCssSelector(".view-title > p").text

        title = arrayOf("\\", "/", ":", "*", "?", "\"", "<", ">", "|").fold(title) {
            title, character -> title.replace(character, " ")
        }

        println("제목은 [${title}]이요")
    }

    // first loop

    var hasNext = true
    while(hasNext) {
        // driver.manage().timeouts().implicitlyWait(2000, TimeUnit.SECONDS);
        var second = 20L;
        second += Random().nextLong() % 10

        Thread.sleep(second * 1000);

        val ps = driver.findElementsByTagName("p")
        for(e in ps) {
            text.append(e.text)
            text.append("\n");
        }

        hasNext = false

        /*if(driver.findElements( By.cssSelector(".view-arrow > a") ).size != 0) {
            println("다음화 버튼 활성화")
            driver.findElementByCssSelector(".viewScroll-Center").click();
            if(title=="") {
                title = driver.findElementByCssSelector(".view-title > p").text
                println("제목은 [${title}]이요")
            }
        }*/
        /*try {
            driver.findElementByCssSelector(".view-arrow > a").isEnabled
        } catch(e: Exception) {
            println("다음화 버튼 활성화")
            driver.findElementByCssSelector(".viewScroll-Center").click();
            if(title=="") {
                title = driver.findElementByCssSelector(".view-title > p").text
                println("제목은 [${title}]이요")
            }
        }*/

        val buttons = driver.findElementsByCssSelector(".view-arrow > a");

        for(button in buttons) {
            if(button.getAttribute("data-testid")=="joa-viewerBottom-right") {
                button.click()
                hasNext = true
                break;
            }
        }
    }

    val writer = File("$title.txt").printWriter()
    writer.println(text)
    writer.close()
    println("끗")
    driver.close()
}