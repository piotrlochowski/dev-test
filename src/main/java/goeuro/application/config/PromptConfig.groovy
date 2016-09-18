package goeuro.application.config

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.support.DefaultPromptProvider;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class PromptConfig extends DefaultPromptProvider {

    @Override
    String getPrompt() { ">>>" }

}