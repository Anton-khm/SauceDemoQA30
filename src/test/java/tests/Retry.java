package tests;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.TestNG;

public class Retry implements IRetryAnalyzer {

    public static int attempt = 1;
    public static final int MAX_RETRY = 1;

    @Override
    public boolean retry(ITestResult iTestResult) {
        if (!iTestResult.isSuccess()) {
            if (attempt < MAX_RETRY) {
                attempt++;
                iTestResult.setStatus(ITestResult.FAILURE);
                System.out.println("Retrying once again");
                return true;
            } else {
                iTestResult.setStatus(ITestResult.FAILURE);
            }
        } else {
            iTestResult.setStatus(ITestResult.SUCCESS);
        }
        return false;
    }
}
