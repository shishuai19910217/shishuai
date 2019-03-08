package com.ido85.master.spider;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class GsonHelper {

	
	/**
	 * 获取GSON转换模式，默认日期格式为“yyyy-MM-dd HH:mm:ss”
	 * 
	 * @return
	 */
	public static Gson getGson() {
		GsonBuilder builder = new GsonBuilder();
		// 不转换没有 @Expose 注解的字段
		builder.excludeFieldsWithoutExposeAnnotation();
		builder.disableHtmlEscaping();
		builder.registerTypeAdapter(Timestamp.class, new TimestampTypeAdapter()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		builder.setPrettyPrinting().create();
		Gson gson = builder.create();
		return gson;
	}

	/**
	 * 获取GSON转换模式，设置时间格式为dataFat
	 * 
	 * @param dataFat
	 *            时间格式
	 * @return
	 */
	public static Gson getGson(String dateFormat) {
		GsonBuilder builder = new GsonBuilder();
		// 不转换没有 @Expose 注解的字段
		builder.excludeFieldsWithoutExposeAnnotation();
		builder.disableHtmlEscaping();
		builder.registerTypeAdapter(Timestamp.class, new TimestampTypeAdapter()).setDateFormat(dateFormat).create();
		Gson gson = builder.create();
		return gson;
	}

	@SuppressWarnings("deprecation")
	public static Object configure(String file, Class c) throws IOException {
		InputStream inputStream = null;
		ByteArrayOutputStream out = null;
		try {
//			System.out.println("llz==="+URLDecoder.decode(GsonHelper.class.getResource(file).getPath()));
//			System.out.println(GsonHelper.class.getResource(file).getPath());
			inputStream = GsonHelper.class.getResourceAsStream("/"+file);
//			inputStream = new FileInputStream(URLDecoder.decode(GsonHelper.class.getResource(file).getPath()));
			String json = "";
			// ByteArrayOutputStream相当于内存输出流
			out = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			// 将输入流转移到内存输出流中
			while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
				out.write(buffer, 0, len);
			}
			// 将内存流转换为字符串
			json = new String(out.toByteArray());

			Gson gson = new Gson();

			Object cfg = gson.fromJson(json, c);
			return cfg;
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}

	public static Object configure(String file, Type t) throws IOException {
		InputStream inputStream = null;
		ByteArrayOutputStream out = null;
		try {
			inputStream = GsonHelper.class.getResourceAsStream(file);
			String json = "";
			// ByteArrayOutputStream相当于内存输出流
			out = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			// 将输入流转移到内存输出流中
			while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
				out.write(buffer, 0, len);
			}
			// 将内存流转换为字符串
			json = new String(out.toByteArray());

			Gson gson = new Gson();

			Object cfg = gson.fromJson(json, t);

			return cfg;
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}

}

/**
 * json包含日期类型的时候的处理方法
 * 
 * @author Administrator
 */
class TimestampTypeAdapter implements JsonSerializer<Timestamp>, JsonDeserializer<Timestamp> {

	private final DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public JsonElement serialize(Timestamp src, Type arg1, JsonSerializationContext arg2) {
		String dateFormatAsString = format.format(new Date(src.getTime()));
		return new JsonPrimitive(dateFormatAsString);
	}

	public Timestamp deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		if (!(json instanceof JsonPrimitive)) {
			throw new JsonParseException("The date should be a string value");
		}

		try {
			Date date = format.parse(json.getAsString());
			return new Timestamp(date.getTime());
		} catch (ParseException e) {
			throw new JsonParseException(e);
		}
	}
}
