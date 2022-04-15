package com.app.todo.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Scanner;

import lombok.Getter;

/**
 * @author Shahrukh
 * @since 15-Apr-2022
 */
@Getter
public class CommonUtils {

	private static Scanner scanner;
	private static SimpleDateFormat sdf;
	
	static {
		scanner = new Scanner(System.in);
		sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
	}
	
}
