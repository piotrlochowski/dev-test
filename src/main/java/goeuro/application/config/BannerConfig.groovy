package goeuro.application.config

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.shell.plugin.support.DefaultBannerProvider
import org.springframework.stereotype.Component

import static org.springframework.shell.support.util.OsUtils.LINE_SEPARATOR

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class BannerConfig extends DefaultBannerProvider {

    @Override
    String getBanner() {
        String banner = this.getClass().getResource('/banner.txt').text
        "${banner}${getVersion()}${LINE_SEPARATOR}"
    }

    @Override
    String getVersion() { "1.0.0" }

    @Override
    String getWelcomeMessage() { "Welcome to Geolocation Suggester" }

}