package runners;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.core.options.Constants.FEATURES_PROPERTY_NAME;
import static io.cucumber.core.options.Constants.GLUE_PROPERTY_NAME;

@Suite
@ConfigurationParameter(
        key = GLUE_PROPERTY_NAME,
        value = "steps, hooks"
)
@ConfigurationParameter(
        key = FEATURES_PROPERTY_NAME,
        value = "classpath:features"
)
public class CucumberTestRunner {



}
