package io.querydsl.utils;

import static io.querydsl.utils.ConditionFlag.ANDCONDITIONS;
import static io.querydsl.utils.ConditionFlag.ORCONDITIONS;
import static io.querydsl.utils.ConditionFlag.SINGLECONDITIONS;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by rai on 17/1/27.
 */
public class ConditionsUtil {
  public static List<String> splitCondition(String queryCondition) {

    String condition = insertOperator(queryCondition);

    String regEx = "\\(|\\||\\)|\"";
    Pattern p = Pattern.compile(regEx);

    String[] words = p.split(condition);
    return Arrays.stream(words)
        .filter(s -> {
          return StringUtils.isNoneBlank(s);
        }).map(s -> {
          s = StringUtils.removeEnd(s, " ");
          s = StringUtils.removeStart(s, " ");
          return s;
        })
        .collect(Collectors.toList());

  }

  private static String insertOperator(String queryCondition) {
    String result = queryCondition;
    if (queryCondition.contains("<>")) {
      result = queryCondition.replaceAll("<>", "|<>|");
    } else if (queryCondition.contains(">=")) {
      result = queryCondition.replaceAll(">=", "|>=|");
    } else if (queryCondition.contains("<=")) {
      result = queryCondition.replaceAll("<=", "|<=|");
    } else if (queryCondition.contains(">")) {
      result = queryCondition.replaceAll(">", "|>|");
    } else if (queryCondition.contains("<")) {
      result = queryCondition.replaceAll("<", "|<|");
    } else if (queryCondition.contains("=")) {
      result = queryCondition.replaceAll("=", "|=|");
    }
    return result;
  }

  public static ConditionFlag getConditionFlag(String queryConditions) {
    ConditionFlag result = SINGLECONDITIONS;
    if (queryConditions.contains(" and ")) {
      result = ANDCONDITIONS;
    } else if (queryConditions.contains(" or ")) {
      result = ORCONDITIONS;
    }
    return result;
  }
}
