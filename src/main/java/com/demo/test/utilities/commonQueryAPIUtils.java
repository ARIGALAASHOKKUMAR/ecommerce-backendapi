package com.demo.test.utilities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.json.JSONObject;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @RamasivaMajji
 */

@Service
public class commonQueryAPIUtils {

	

	public static Map<String, Object> apiServicemap(String apiServiceName, Map<String, Object> repomethod) {
		Map<String, Object> final_data = new LinkedHashMap<String, Object>();
		try {
			if (repomethod.size() > 0) {
				final_data.put("status", true);
				final_data.put("ResponseCode", "01");
				final_data.put("ResponseDesc", "data found");
				final_data.put(apiServiceName, repomethod);
				final_data.put("data_count", repomethod.size());
			} else {
				final_data.put("status", false);
				final_data.put("ResponseCode", "03");
				final_data.put("ResponseDesc", "No data found");
				final_data.put(apiServiceName, "No data found");
				final_data.put("data_count", 0);
			}
		} catch (Exception e) {

			final_data.put("status", false);
			final_data.put("ResponseCode", "02");
			final_data.put("ResponseDesc", "Internal server issue");
			final_data.put(apiServiceName, "No data found");
			final_data.put("data_count", 0);
		}
		return final_data;
	}

	public static Map<String, Object> apiServicenew(String apiServiceName, List<Map<String, Object>> repomethod) {
		Map<String, Object> final_data = new LinkedHashMap<String, Object>();
		try {
			if (repomethod.size() > 0) {
				final_data.put("status", true);
				final_data.put("ResponseCode", "01");
				final_data.put("ResponseDesc", "data found");
				final_data.put("data", repomethod);
				final_data.put("data_count", repomethod.size());
			} else {
				final_data.put("status", false);
				final_data.put("ResponseCode", "03");
				final_data.put("ResponseDesc", "No data found");
				final_data.put("data", "No data found");
				final_data.put("data_count", 0);
			}
		} catch (Exception e) {

			final_data.put("status", false);
			final_data.put("ResponseCode", "02");
			final_data.put("ResponseDesc", "Internal server issue");
			final_data.put(apiServiceName, "No data found");
			final_data.put("data_count", 0);
		}
		return final_data;
	}

	public static Map<String, Object> apiServiceMulti(List<String> apiServiceNameList,
			List<List<Map<String, Object>>> repomethodList) {
		Map<String, Object> final_data = new LinkedHashMap<String, Object>();
		try {
			final_data.put("status", true);
			final_data.put("ResponseCode", "01");
			final_data.put("ResponseDesc", "Multiple lists found");

			String apiServiceName = "";

			for (int i = 0; i < repomethodList.size(); i++) {

				apiServiceName = apiServiceNameList.get(i);

				if (repomethodList.get(i).size() > 0) {
					final_data.put(apiServiceName + "Status", true);
					final_data.put(apiServiceName, repomethodList.get(i));
				} else {
					final_data.put(apiServiceName + "Status", false);
					final_data.put(apiServiceName, "No data found");
				}
			}
		} catch (Exception e) {
			final_data = new LinkedHashMap<String, Object>();
			final_data.put("status", false);
			final_data.put("ResponseCode", "02");
			final_data.put("ResponseDesc", "Internal server issue");
		}
		return final_data;

		// ************************************ Sample Controller
		// *****************************
		/*
		 * @GetMapping("/t") Map<String, Object> getMethod(@RequestParam Integer
		 * reqParam) { List<List<Map<String, Object>>> mulDataList =
		 * List.of(repo.get1(reqParam),repo.get2(reqParam)); List<String> mulNamesList =
		 * (List.of("get1Name","get2Name")); return
		 * commonQueryAPIUtils.apiServiceMulti(mulNamesList, mulDataList); }
		 */
	}

	public static Map<String, Object> apiServiceDelete(Integer deleteQueryfromRepo) {
		Map<String, Object> final_data = new LinkedHashMap<String, Object>();
		try {
			if (deleteQueryfromRepo > 0) {
				final_data.put("status", true);
				final_data.put("ResponseCode", "01");
				final_data.put("ResponseDesc", "Successfully Deleted");
			} else {
				final_data.put("status", false);
				final_data.put("ResponseCode", "03");
				final_data.put("ResponseDesc", "No records found to delete");
			}
		} catch (Exception e) {

			final_data.put("status", false);
			final_data.put("ResponseCode", "02");
			final_data.put("ResponseDesc", "Deletion Failed Due To: Internal Server Issue");
		}
		return final_data;
	}

	public static Map<String, Object> apiServiceUpdate(Integer updateQueryfromRepo) {
		Map<String, Object> final_data = new LinkedHashMap<String, Object>();
		try {
			if (updateQueryfromRepo > 0) {
				final_data.put("status", true);
				final_data.put("ResponseCode", "01");
				final_data.put("ResponseDesc", "Successfully Updated");
			} else {
				final_data.put("status", false);
				final_data.put("ResponseCode", "03");
				final_data.put("ResponseDesc", "No records found to update");
			}
		} catch (Exception e) {

			final_data.put("status", false);
			final_data.put("ResponseCode", "02");
			final_data.put("ResponseDesc", "Updation Failed Due To: Internal Server Issue");
		}
		return final_data;
	}



	public static String validationServiceWithIndex(List<Object> values, List<String> names, Integer index) {
		String message = "";
		try {
			for (int i = 0; i < values.size(); i++) {
				if (Objects.isNull(values.get(i)) || "".equals(values.get(i))) {
					message += names.get(i) + " is required at index " + index + ". ";
				}
			}
		} catch (Exception e) {

			message = "Internal Server Issue";
		}
		return message;
	}

	public static ResponseEntity<Map<String, Object>> successResponse() {
		Map<String, Object> final_data = new LinkedHashMap<String, Object>();
		final_data.put("status", true);
		final_data.put("ResponseCode", "01");
		final_data.put("ResponseDesc", "Successfully Submitted");

		return ResponseEntity.ok().body(final_data);
	}

	public static ResponseEntity<?> failureResponse(String errorMessage) {
		Map<String, Object> final_data = new LinkedHashMap<String, Object>();

		final_data.put("status", false);

		if ("02".equals(errorMessage) || errorMessage.isEmpty()) {
			final_data.put("ResponseCode", "02");
			final_data.put("ResponseDesc", "Submission Failed Due To: Internal Server Issue");
		} else {
			final_data.put("ResponseCode", "03");
			final_data.put("ResponseDesc", "Submission Failed Due To: " + errorMessage);
		}

		return ResponseEntity.ok().body(final_data);
	}

	public static Map<String, Object> failureResponsemap(String errorMessage) {
		Map<String, Object> final_data = new LinkedHashMap<String, Object>();

		final_data.put("status", false);

		if ("02".equals(errorMessage) || errorMessage.isEmpty()) {
			final_data.put("ResponseCode", "02");
			final_data.put("ResponseDesc", "Submission Failed Due To: Internal Server Issue");
		} else {
			final_data.put("ResponseCode", "03");
			final_data.put("ResponseDesc", "Submission Failed Due To: " + errorMessage);
		}

		return final_data;
	}

	public static ResponseEntity<?> unAuthorisedResponse() {
		Map<String, Object> final_data = new LinkedHashMap<String, Object>();

		final_data.put("status", false);
		final_data.put("ResponseCode", "401");
		final_data.put("ResponseDesc", "Unauthorized request");

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(final_data);
	}

	

	public static Map<String, Object> manualResponsemap(String code, String message) {
		Map<String, Object> final_data = new LinkedHashMap<String, Object>();
		if ("01".equals(code)) {
			final_data.put("status", true);
		} else {
			final_data.put("status", false);
		}
		final_data.put("ResponseCode", code);
		final_data.put("ResponseDesc", message);

		return final_data;
	}

	public static ResponseEntity<Map<String, Object>> catchResponse(Exception ex) {
		Map<String, Object> final_data = new LinkedHashMap<String, Object>();
		LocalDateTime logTime = LocalDateTime.now();
		String logId = logTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
		final_data.put("status", false);
		final_data.put("scode", "02");
		final_data.put("sdesc", "Failed Due To: Internal Server Issue. Time: " + logTime + ". Log Id: " + logId);
		System.out.println("====> ERROR :::: LOG ID: " + logId + " :::: LOG TIME: " + logTime + " :::: ERROR <====");
		exceptionLog(ex);
		return ResponseEntity.ok().body(final_data);
	}

	public static Map<String, Object> exceptionLog(Exception ex) {
		Map<String, Object> final_data = new LinkedHashMap<String, Object>();
		List<Map<String, Object>> ex_list = new ArrayList<Map<String, Object>>();
		String currentPackage = commonQueryAPIUtils.class.getPackageName().trim();
		String basePackage = currentPackage.substring(0, currentPackage.indexOf('.', currentPackage.indexOf('.') + 1));
		final_data.put("base_package", basePackage);
		final_data.put("exception", ex.getClass().getName());
		final_data.put("message", ex.getMessage());
		System.out.println(":::Package::: " + basePackage + " :::exception::: " + ex.getClass().getName()
				+ " :::message::: " + ex.getMessage());
		StackTraceElement[] stackTrace = ex.getStackTrace();
		for (StackTraceElement element : stackTrace) {
			if (element.getClassName().contains(basePackage)) {
				Map<String, Object> inner_data = new LinkedHashMap<String, Object>();
				inner_data.put("exception_class", element.getClassName());
				inner_data.put("method", element.getMethodName());
				inner_data.put("line", element.getLineNumber());
				ex_list.add(inner_data);
				System.out.println("=> :::ExClass::: " + element.getClassName() + " :::Method::: "
						+ element.getMethodName() + " :::Line Number::: " + element.getLineNumber());
			}
		}
		final_data.put("ex_list", ex_list);
		return final_data;
	}

//	public static Map<String, Object> exceptionLog(Exception ex) {
//		Map<String, Object> final_data = new LinkedHashMap<String, Object>();
//		List<Map<String, Object>> ex_list = new ArrayList<Map<String, Object>>();
//
//		String currentPackage = CommonQueryAPIUtils.class.getPackageName().trim();
//		String basePackage = currentPackage.substring(0, currentPackage.indexOf('.', currentPackage.indexOf('.') + 1));
//
////		final_data.put("user", );
////		final_data.put("timestamp", );
////		final_data.put("ip", );
//		final_data.put("base_package", basePackage);
//		final_data.put("exception", ex.getClass().getName());
//		final_data.put("message", ex.getMessage());
//
//		System.out.println(":::Package::: " + basePackage + " :::ExClass::: " + ex.getClass().getName()
//				+ " :::message::: " + ex.getMessage());
//
//		StackTraceElement[] stackTrace = ex.getStackTrace();
//		for (StackTraceElement element : stackTrace) {
//
//			if (element.getClassName().contains(basePackage)) {
//
//				Map<String, Object> inner_data = new LinkedHashMap<String, Object>();
//
//				inner_data.put("exception_class", element.getClassName());
//				inner_data.put("method", element.getMethodName());
//				inner_data.put("line", element.getLineNumber());
//				ex_list.add(inner_data);
//
//				System.out.println("=> :::ExClass::: " + element.getClassName() + " :::Method::: "
//						+ element.getMethodName() + " :::Line Number::: " + element.getLineNumber());
//			}
//		}
//
//		final_data.put("ex_list", ex_list);
//		return final_data;
//	}

	public static String validatePojoProMax(ObjectNode objectNode) {
		String errMsg = "";
		StringBuilder errMsgBuilder = new StringBuilder();
		objectNode.fieldNames().forEachRemaining(key -> {
			JsonNode value = objectNode.get(key);
			if (value == null || value.isNull() || value.isTextual() && value.textValue().isEmpty()) {
				errMsgBuilder.append(key).append(" is required. ");
			}
		});
		errMsg = errMsgBuilder.toString();
		return errMsg;
	}

	public static Boolean isStringEmpty(String value) {
		if (value == null || "null".equals(value) || "".equals(value) || value.isEmpty() || value.isBlank()) {
			return true;
		}
		return false;
	}

	public static String validateEntity(Object entity) {
		String message = "";
		try {
			Field[] fields = entity.getClass().getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				Object value = field.get(entity);
				if (value == null || isStringEmpty(value.toString())) {
					message += field.getName() + " is required. ";
				}
			}

		} catch (Exception e) {
			message = "Internal Server Issue at Validation";
		}
		return message;
	}

	public static String validateEntity(Object entity, int index) {
		String message = "";
		try {
			Field[] fields = entity.getClass().getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				Object value = field.get(entity);
				if (value == null || isStringEmpty(value.toString())) {
					message += field.getName() + " is required at index " + index + ". ";
				}
			}

		} catch (Exception e) {
			message = "Internal Server Issue at Validation";
		}
		return message;
	}

	public static String validateEntityFor(Object entity, String[] keys) {
		String message = "";
		try {
			List<Field> fields = new ArrayList<Field>();
			for (String key : keys) {
				fields.add(entity.getClass().getDeclaredField(key));
			}
			for (Field field : fields) {
				field.setAccessible(true);
				Object value = field.get(entity);
				if (value == null || isStringEmpty(value.toString())) {
					message += field.getName() + " is required. ";
				}
			}

		} catch (Exception e) {
			message = "Internal Server Issue at Validation";
		}
		return message;
	}

	public static String validateEntityFor(Object entity, String[] keys, int index) {
		String message = "";
		try {
			List<Field> fields = new ArrayList<Field>();
			for (String key : keys) {
				fields.add(entity.getClass().getDeclaredField(key));
			}
			for (Field field : fields) {
				field.setAccessible(true);
				Object value = field.get(entity);
				if (value == null || isStringEmpty(value.toString())) {
					message += field.getName() + " is required at index " + index + ". ";
				}
			}

		} catch (Exception e) {
			message = "Internal Server Issue at Validation";
		}
		return message;
	}

	public static String validateEntityExcept(Object entity, String[] keys) {
		String message = "";
		try {
			List<Field> fields = Arrays.asList(entity.getClass().getDeclaredFields());
			Set<String> excludedKeys = new HashSet<>(Arrays.asList(keys));

			for (Field field : fields) {
				if (excludedKeys.contains(field.getName())) {
					continue;
				}
				field.setAccessible(true);
				Object value = field.get(entity);

				if (value == null || isStringEmpty(value.toString())) {
					message += field.getName() + " is required. ";
				}
			}
		} catch (Exception e) {
			message = "Internal Server Issue at Validation";
		}
		return message;
	}

	public static String validateEntityExcept(Object entity, String[] keys, int index) {
		String message = "";
		try {
			List<Field> fields = Arrays.asList(entity.getClass().getDeclaredFields());
			Set<String> excludedKeys = new HashSet<>(Arrays.asList(keys));

			for (Field field : fields) {
				if (excludedKeys.contains(field.getName())) {
					continue;
				}
				field.setAccessible(true);
				Object value = field.get(entity);

				if (value == null || isStringEmpty(value.toString())) {
					message += field.getName() + " is required at index " + index + ". ";
				}
			}
		} catch (Exception e) {
			message = "Internal Server Issue at Validation";
		}
		return message;
	}

	public static byte[] callApiAndGetBytes(String apiUrl) {
		RestTemplate restTemplate = new RestTemplate();

		// Make a GET request to the API and get the response as a byte array
		ResponseEntity<byte[]> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, null, byte[].class);

		if (responseEntity.getStatusCode().is2xxSuccessful()) {
			return responseEntity.getBody();
		} else {
			System.err.println("API request failed. Status code: " + responseEntity.getStatusCode());
			return null;
		}
	}

	public static Map<String, Object> uploadFileToAWSS3Bucket(byte[] fileByteArray, String fileNameWithExtension,
			String apiUrl) {

		Map<String, Object> responseMap = new LinkedHashMap<String, Object>();

		ByteArrayResource fileResource = new ByteArrayResource(fileByteArray) {
			@Override
			public String getFilename() {
				return fileNameWithExtension;
			}
		};

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		MultiValueMap<String, Object> payload = new LinkedMultiValueMap<>();
		payload.add("file", fileResource);

		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(payload, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(apiUrl, requestEntity, String.class);

		if (responseEntity.getStatusCode().is2xxSuccessful()) {
			System.out.println("File uploaded successfully! Path is " + responseEntity.getBody());
			responseMap.put("status", true);
			responseMap.put("path", responseEntity.getBody());
		} else {
			System.out.println("Failed to upload File. Response: " + responseEntity.getBody());
			responseMap.put("status", false);
		}

		return responseMap;
	}

	public static Map<String, Object> uploadFileToAWSS3Bucketwithurl(String filePath, String fileNameWithExtension,
			String fileUrl, String apiUrl) {

		Map<String, Object> responseMap = new LinkedHashMap<String, Object>();

//		ByteArrayResource fileResource = new ByteArrayResource(fileByteArray) {
//			@Override
//			public String getFilename() {
//				return fileNameWithExtension;
//			}
//		};

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> payload = new LinkedHashMap<String, Object>();
		payload.put("fileUrl", fileUrl);
		payload.put("fileName", fileNameWithExtension);
		payload.put("filePath", filePath);

		HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(payload, headers);

		RestTemplate restTemplate = new RestTemplate();
		try {
			ResponseEntity<String> responseEntity = restTemplate.postForEntity(apiUrl, requestEntity, String.class);

			if (responseEntity.getStatusCode().is2xxSuccessful()) {
				System.out.println("File uploaded successfully! Path is " + responseEntity.getBody());
				responseMap.put("status", true);
				responseMap.put("path", responseEntity.getBody());
			} else {
				System.out.println("Failed to upload File. Response: " + responseEntity.getBody());
				responseMap.put("status", false);
			}
		} catch (HttpServerErrorException ex) {
			if (ex.getStatusCode() == HttpStatus.GATEWAY_TIMEOUT) {
				System.out.println("Gateway Timeout occurred, but the file might still be uploaded.");
				fileNameWithExtension = (filePath + "/" + fileNameWithExtension).replaceAll("\\s", "");
				responseMap.put("status", true);
				responseMap.put("path", fileNameWithExtension);
			} else {
				// Handle other 5XX errors
				responseMap.put("status", false);
			}
		}
		return responseMap;
	}

	public static BufferedImage createImageWithText(String text) {
		int width = 140;
		int height = 75;

		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = bufferedImage.createGraphics();

		// Fill the background
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, width, height);

		// Draw the text
		graphics.setColor(Color.BLACK);
		Font font = new Font("Georgia", Font.BOLD, 30);
		graphics.setFont(font);
		graphics.drawString(text, 10, 50);

		// Dispose the graphics object to release resources
		graphics.dispose();

		return bufferedImage;
	}

	public static String randomString(int length) {
		SecureRandom rnd = new SecureRandom();
		String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}

	public static String randomNumber(int length) {
		SecureRandom rnd = new SecureRandom();
		String AB = "0123456789";
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}

	public static ObjectMapper createObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();

		// Configure Date format
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		mapper.setDateFormat(dateFormat);

		return mapper;
	}

	public static String sendJnbOtp(String mobileNo, String templateId, String message, JdbcTemplate jdbcTemplate) {

		StringBuffer response = new StringBuffer();
		final String pwd = "password";
		String url = "https://www.smsstriker.com/API/sms.php?username=jnbotp&" + pwd + "=Jnb_871&from=JNBOTP&to="
				+ mobileNo + "&msg=" + message + "&type=1&template_id=" + templateId;
		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
			response.append(responseEntity);

			String sql = "INSERT INTO candidate_otp_log(mobile, time_stamp, message, response) VALUES (?, now(), ?, ?)";
			jdbcTemplate.update(sql, mobileNo, message, response.toString());

		} catch (Exception e) {
			catchResponse(e);
			System.out.println("sendJnbOtp Failure::::::");
		}

		return response.toString();

	}
	
	public static String causelistmessage(String mobileNo, String templateId, String message, JdbcTemplate jdbcTemplate) {

		StringBuffer response = new StringBuffer();
		final String pwd = "password";
		Integer caseNumber=10;
		String hearingDate="21-03-2025";
		String key="2025-03-21|0";
		mobileNo="6301691750";
		templateId="1407174229052079293";
		message = "Sir/Madam, \r\n"
				+ "In High Court " + caseNumber + " cases are coming for hearing on " + hearingDate + ".Click this link https://nyayam.apcfss.in/caseSms/?key=" + key + " to see the cases list .\r\n"
						+ "Further information regarding the cases can be accessed through the Nyaym Tile available within Nidhi Portal";
		String url = "https://www.smsstriker.com/API/sms.php?username=jnbotp&" + pwd + "=Jnb_871&from=APGOVT&to="
				+ mobileNo + "&msg=" + message + "&type=1&template_id=" + templateId;
		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
			response.append(responseEntity);

//			String sql = "INSERT INTO candidate_otp_log(mobile, time_stamp, message, response) VALUES (?, now(), ?, ?)";
//			jdbcTemplate.update(sql, mobileNo, message, response.toString());

		} catch (Exception e) {
			catchResponse(e);
			System.out.println("sendJnbOtp Failure::::::");
		}

		return response.toString();

	}

	public static String sendMappingNotification(String mobileno, String caseDetails, JdbcTemplate jdbcTemplate) {
		String url = "https://apps.office24by7.com/v1/common/API/apis";
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("api_name", "Whatsappnotifications");
		body.add("apiKey", "7788dbff-820f-428f-815f-4f93ed609454");
		body.add("user_auth_token", "9374859d5c2f1878d7a50a4e5bc3252a");
		body.add("wbn", "919121148056");
		body.add("template_id", "1");
		body.add("phone_number", "91" + mobileno);
		body.add("format", "json");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
			JSONObject json = new JSONObject(response.getBody());
			String sql = "INSERT INTO public.wtsp_logs\r\n"
					+ "(mobileno, case_details, response, payload, inserted_time)\r\n" + "VALUES(?,?,?,?,now());";
			jdbcTemplate.update(sql, mobileno, caseDetails, response.getBody(),
					new ObjectMapper().writeValueAsString(body));
			if (json.get("statusCode").toString().equals("200")) {
				return "success";
			} else {
				return "fail";
			}
		} catch (Exception e) {
			return "fail";
		}
	}

	public static String sendAllcesesNotification(String mobileno, String caseDetails, String empname, String casetype,
			String casenos, JdbcTemplate jdbcTemplate) {
		String url = "https://apps.office24by7.com/v1/common/API/apis";
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("api_name", "Whatsappnotifications");
		body.add("apiKey", "7788dbff-820f-428f-815f-4f93ed609454");
		body.add("user_auth_token", "9374859d5c2f1878d7a50a4e5bc3252a");
		body.add("wbn", "919121148056");
		body.add("template_id", "1");
		body.add("phone_number", "91" + mobileno);
		body.add("variables", empname + "," + casetype + "'s" + "," + casenos);
		body.add("format", "json");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
			JSONObject json = new JSONObject(response.getBody());
			String sql = "INSERT INTO public.wtsp_logs\r\n"
					+ "(mobileno, case_details, response, payload, inserted_time)\r\n" + "VALUES(?,?,?,?,now());";
			jdbcTemplate.update(sql, mobileno, caseDetails, response.getBody(),
					new ObjectMapper().writeValueAsString(body));
			if (json.get("statusCode").toString().equals("200")) {
				return "success";
			} else {
				return "fail";
			}
		} catch (Exception e) {
			return "fail";
		}
	}

	public static Map<String, Object> deleteFileFromAWSS3Bucketwithurl(String filePath) {

		Map<String, Object> responseMap = new LinkedHashMap<String, Object>();

		String apiUrl = "https://swapi.dev.nidhi.apcfss.in/socialwelfaredms/user-defined-path/file-delete/" + filePath;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> payload = new LinkedHashMap<String, Object>();
		payload.put("filePath", filePath);

		HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(payload, headers);

		RestTemplate restTemplate = new RestTemplate();
		try {
			ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.DELETE, requestEntity,
					String.class);

			if (responseEntity.getStatusCode().is2xxSuccessful()) {
				System.out.println("File deleted successfully! Path is " + responseEntity.getBody());
				responseMap.put("status", true);
				responseMap.put("path", filePath + " of " + responseEntity.getBody());
			} else {
				System.out.println("Failed to delete File. Response: " + responseEntity.getBody());
				responseMap.put("status", false);
			}
		} catch (HttpServerErrorException ex) {
			ex.printStackTrace();
			if (ex.getStatusCode() == HttpStatus.GATEWAY_TIMEOUT) {
				System.out.println("Gateway Timeout occurred, but the file might still be deleted.");
				responseMap.put("status", true);
				responseMap.put("path", filePath);
			} else {
				responseMap.put("status", false);
			}
		}
		return responseMap;
	}
	
	
	
	//////////////////////////////////////sai
	
	public static ResponseEntity<?> manualResponse(String code, String message) {
		Map<String, Object> final_data = new LinkedHashMap<String, Object>();
		if ("01".equals(code)) {
			final_data.put("status", true);
		} else {
			final_data.put("status", false);
		}
		final_data.put("code", code);
		final_data.put("message", message);

		return ResponseEntity.ok().body(final_data);
	}
	
	public static ResponseEntity<Map<String, Object>> sResponse(String message) {
		Map<String, Object> final_data = new LinkedHashMap<String, Object>();
		final_data.put("status", true);
		final_data.put("code", "01");
		final_data.put("message", message);

		return ResponseEntity.ok().body(final_data);
	}
	
	
	public static ResponseEntity<Map<String, Object>> fDynamicResponse(String message) {
		Map<String, Object> final_data = new LinkedHashMap<String, Object>();
		final_data.put("status", false);
		final_data.put("code", "02");
		final_data.put("message", message);

		return ResponseEntity.ok().body(final_data);
	}
	
	public static ResponseEntity<?> fStaticResponse(String errorMessage) {
		Map<String, Object> final_data = new LinkedHashMap<String, Object>();

		final_data.put("status", false);

		if ("02".equals(errorMessage) || errorMessage.isEmpty()) {
			final_data.put("code", "02");
			final_data.put("message", "Submission Failed Due To: Internal Server Issue");
		} else {
			final_data.put("code", "03");
			final_data.put("message", "Submission Failed Due To: " + errorMessage);
		}

		return ResponseEntity.ok().body(final_data);
	}
	
	
	public static ResponseEntity<Map<String, Object>> fCatchResponse(Exception ex) {
		Map<String, Object> final_data = new LinkedHashMap<String, Object>();
		LocalDateTime logTime = LocalDateTime.now();
		String logId = logTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
		final_data.put("status", false);
		final_data.put("code", "02");
		final_data.put("message", "Failed Due To: Internal Server Issue. Time: " + logTime + ". Log Id: " + logId);
		System.out.println("====> ERROR :::: LOG ID: " + logId + " :::: LOG TIME: " + logTime + " :::: ERROR <====");
		exceptionLog(ex);
		return ResponseEntity.ok().body(final_data);
	}
	
	
	public static Map<String, Object> apiService(String apiServiceName, List<Map<String, Object>> repomethod) {
		Map<String, Object> final_data = new LinkedHashMap<String, Object>();
		try {
			if (repomethod.size() > 0) {
				final_data.put("status", true);
				final_data.put("code", "01");
				final_data.put("message", "data found");
				final_data.put(apiServiceName, repomethod);
				final_data.put("data_count", repomethod.size());
			} else {
				final_data.put("status", false);
				final_data.put("code", "03");
				final_data.put("message", "No data found");
				final_data.put(apiServiceName, "No data found");
				final_data.put("data_count", 0);
			}
		} catch (Exception e) {

			final_data.put("success", false);
			final_data.put("ResponseCode", "02");
			final_data.put("ResponseDesc", "Internal server issue");
			final_data.put(apiServiceName, "No data found");
			final_data.put("data_count", 0);
		}
		return final_data;
	}
	
	
	public static String validationService(List<Object> values, List<String> names) {
		String message = "";
		try {
			for (int i = 0; i < values.size(); i++) {
				if (Objects.isNull(values.get(i)) || "".equals(values.get(i))) {
					message += names.get(i) + " is required. ";
				}
			}
		} catch (Exception e) {

			message = "Internal Server Issue";
		}
		return message;
	}
	
	
	
	
	
	

}
