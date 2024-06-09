package com.rest.crm.util;

import java.util.ArrayList;
import java.util.List;
import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MaskingPatternLayout extends PatternLayout {

	private Pattern multilinePattern;
	private List<String> maskPatterns = new ArrayList<>();

	public void addMaskPattern(String maskPattern) { 
		maskPatterns.add(maskPattern);
		multilinePattern = Pattern.compile(String.join("|", maskPatterns), 
				Pattern.MULTILINE);
	}

	@Override
	public String doLayout(ILoggingEvent event) {
		return maskMessage(super.doLayout(event)); 
	}

	
	private String maskMessage(String message) {
		if (multilinePattern == null) {
			return message;
		}
		StringBuilder sb = new StringBuilder(message);
		Matcher matcher = multilinePattern.matcher(sb);
		while (matcher.find()) {
			if (matcher.group().contains("email") || matcher.group().contains("phoneNumber")) {
				maskFieldData(sb, matcher);
			}
		}
		return sb.toString();
	} 

	private void maskFieldData(StringBuilder sb, Matcher matcher) {
		String targetExpression = matcher.group();
		String[] split = null;
		if (targetExpression.contains("=")) {
			split = targetExpression.split("=");
		}
		else {
			split = targetExpression.split(":");
		}
		if (split != null) {
			String pan = split[1];
			String maskedPan = getMaskedPan(pan);
			int start = matcher.start() + split[0].length() + 1;
			int end = matcher.end();
			sb.replace(start, end, maskedPan);
		}
	}
	
	private String getMaskedPan(String pan) {
		pan = pan.replaceAll(pan, "*******");
		return pan;
	}
}