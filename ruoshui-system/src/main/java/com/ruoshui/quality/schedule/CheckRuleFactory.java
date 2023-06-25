package com.ruoshui.quality.schedule;



import com.aspose.words.net.System.Data.DataException;
import com.ruoshui.quality.schedule.rules.RuleItem;
import com.ruoshui.quality.schedule.rules.RuleItemRegistry;

import java.util.Optional;

public class CheckRuleFactory {

    private static final RuleItemRegistry RULE_ITEM_REGISTRY = new RuleItemRegistry();

    public CheckRuleFactory() {
    }

    public static RuleItem getRuleItem(String code) {
        return Optional.ofNullable(RULE_ITEM_REGISTRY.getRuleItem(code)).orElseThrow(() -> new DataException(String.format("%s not supported.", code)));
    }
}
