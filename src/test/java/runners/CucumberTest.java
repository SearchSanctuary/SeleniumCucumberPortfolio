package runners;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.core.options.Constants.*;

@Suite
@ConfigurationParameter(
        key = GLUE_PROPERTY_NAME,
        value = "steps, hooks"
)
@ConfigurationParameter(
        key = FEATURES_PROPERTY_NAME,
        value = "classpath:features"
)
@ConfigurationParameter(
        key = "cucumber.execution.parallel.enabled",
        value = "true"
)
@ConfigurationParameter(
        key = "cucumber.execution.parallel.config.fixed.parallelism",
        value = "2"
)
@ConfigurationParameter(
        key = PLUGIN_PROPERTY_NAME,
        value = "html:target/cucumber-reports/cucumber.html"
)
public class CucumberTest {



}
