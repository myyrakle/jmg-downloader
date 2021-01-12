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
    System.setProperty("webdriver.chrome.driver", "C:\\Users\\sssan\\Code\\Java\\j-downloader\\chromedriver.exe");

    //val link = "https://m.joara.com/viewer?cid=Ni+ccy7qQiijC4A060ZEmg==&bookCode=1502867&sortno=1";

    print("1화 링크좀 복붙해보소: ")
    val link = Scanner(System.`in`).nextLine()

    val text = StringBuilder();

    val driver = ChromeDriver()
    // 창 최대화
    driver.manage().window().maximize()

    // first loop
    driver.get(link);
    driver.manage().timeouts().implicitlyWait(2000, TimeUnit.SECONDS);

    driver.findElementByCssSelector(".pc-guide-action > a").click()
    //driver.findElementByCssSelector(".action-bar > a").click()

    val ps = driver.findElementsByTagName("p")
    for(e in ps) {
        text.append(e.text)
        text.append("\n");
    }

    // 다음화 버튼 뜨게 클릭
    Thread.sleep(3000);
    driver.findElementByCssSelector(".viewScroll-Center").click();
    val title = driver.findElementByCssSelector(".view-title > p").text
    println("제목은 [${title}]이요")

    // first loop

    var hasNext = true
    while(hasNext) {
        // driver.manage().timeouts().implicitlyWait(2000, TimeUnit.SECONDS);
        val ps = driver.findElementsByTagName("p")
        for(e in ps) {
            text.append(e.text)
            text.append("\n");
        }

        var second = 20L;
        second += Random().nextLong() % 10

        Thread.sleep(second * 1000);
        hasNext = false

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