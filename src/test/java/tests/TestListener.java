package tests;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.TestNG;

import java.util.concurrent.TimeUnit;

import static tests.AllureUtils.takeScreenshot;
import static tests.Retry.MAX_RETRY;
import static tests.Retry.attempt;

@Log4j2
public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult iTestResult) {
        log.info("======================================== STARTING TEST {} ========================================%n", iTestResult.getName());
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        log.info("======================================== FINISHED TEST {} Duration: {} ========================================%n", iTestResult.getName(),
                getExecutionTime(iTestResult));
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        log.error("======================================== FAILED TEST {} Duration: {} ========================================%n", iTestResult.getName(),
                getExecutionTime(iTestResult));
            log.error("I am in onTestFailure method {} failed", getTestMethodName(iTestResult));
            if (attempt < MAX_RETRY) {
                attempt++;
                TestNG tng = new TestNG();
                tng.setDefaultTestName("RETRY TEST");
                Class[] classes1 = { iTestResult.getTestClass().getRealClass() };
                tng.setTestClasses(classes1);
                tng.addListener(new TestListener());
                tng.run();
        }
        ITestContext context = iTestResult.getTestContext();
        WebDriver driver = (WebDriver) context.getAttribute("driver");
        takeScreenshot(driver);
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        log.warn("======================================== SKIPPING TEST {} ========================================%n", iTestResult.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {

    }

    private long getExecutionTime(ITestResult iTestResult) {
        long executionTime = TimeUnit.MILLISECONDS.toSeconds(iTestResult.getEndMillis() - iTestResult.getStartMillis());
        log.info("Getting execution time: {}s", executionTime);
        return executionTime;
    }

    private String getTestMethodName(ITestResult iTestResult) {
        log.info("Getting test method name");
        return iTestResult.getMethod().getConstructorOrMethod().getMethod().getName();
    }
}
