package seigneurnecron.minecraftmods.core.reflection;

import java.lang.reflect.Field;

/**
 * LICENCE : http://sourceforge.net/p/mc-stargate-mod/code/HEAD/tree/trunk/Sources/Licences/licence.txt
 * 
 * @author Seigneur Necron
 */
public final class Reflection {
	
	// Construtors :
	
	private Reflection() {
		// This class don't have to be instanciated, it only contains static methods.
	}
	
	// Getters :
	
	public static Object get(Class clazz, Object object, String name) throws ReflectionException {
		try {
			Field field = clazz.getDeclaredField(name);
			field.setAccessible(true);
			return field.get(object);
		}
		catch(Exception argh) {
			throw new ReflectionException(argh);
		}
	}
	
	public static boolean getBoolean(Class clazz, Object object, String name) throws ReflectionException {
		try {
			Field field = clazz.getDeclaredField(name);
			field.setAccessible(true);
			return field.getBoolean(object);
		}
		catch(Exception argh) {
			throw new ReflectionException(argh);
		}
	}
	
	public static byte getByte(Class clazz, Object object, String name) throws ReflectionException {
		try {
			Field field = clazz.getDeclaredField(name);
			field.setAccessible(true);
			return field.getByte(object);
		}
		catch(Exception argh) {
			throw new ReflectionException(argh);
		}
	}
	
	public static char getChar(Class clazz, Object object, String name) throws ReflectionException {
		try {
			Field field = clazz.getDeclaredField(name);
			field.setAccessible(true);
			return field.getChar(object);
		}
		catch(Exception argh) {
			throw new ReflectionException(argh);
		}
	}
	
	public static double getDouble(Class clazz, Object object, String name) throws ReflectionException {
		try {
			Field field = clazz.getDeclaredField(name);
			field.setAccessible(true);
			return field.getDouble(object);
		}
		catch(Exception argh) {
			throw new ReflectionException(argh);
		}
	}
	
	public static float getFloat(Class clazz, Object object, String name) throws ReflectionException {
		try {
			Field field = clazz.getDeclaredField(name);
			field.setAccessible(true);
			return field.getFloat(object);
		}
		catch(Exception argh) {
			throw new ReflectionException(argh);
		}
	}
	
	public static int getInt(Class clazz, Object object, String name) throws ReflectionException {
		try {
			Field field = clazz.getDeclaredField(name);
			field.setAccessible(true);
			return field.getInt(object);
		}
		catch(Exception argh) {
			throw new ReflectionException(argh);
		}
	}
	
	public static long getLong(Class clazz, Object object, String name) throws ReflectionException {
		try {
			Field field = clazz.getDeclaredField(name);
			field.setAccessible(true);
			return field.getLong(object);
		}
		catch(Exception argh) {
			throw new ReflectionException(argh);
		}
	}
	
	public static short getShort(Class clazz, Object object, String name) throws ReflectionException {
		try {
			Field field = clazz.getDeclaredField(name);
			field.setAccessible(true);
			return field.getShort(object);
		}
		catch(Exception argh) {
			throw new ReflectionException(argh);
		}
	}
	
	// Setters :
	
	public static void set(Class clazz, Object object, String name, Object value) throws ReflectionException {
		try {
			Field field = clazz.getDeclaredField(name);
			field.setAccessible(true);
			field.set(object, value);
		}
		catch(Exception argh) {
			throw new ReflectionException(argh);
		}
	}
	
	public static void setBoolean(Class clazz, Object object, String name, boolean value) throws ReflectionException {
		try {
			Field field = clazz.getDeclaredField(name);
			field.setAccessible(true);
			field.setBoolean(object, value);
		}
		catch(Exception argh) {
			throw new ReflectionException(argh);
		}
	}
	
	public static void setByte(Class clazz, Object object, String name, byte value) throws ReflectionException {
		try {
			Field field = clazz.getDeclaredField(name);
			field.setAccessible(true);
			field.setByte(object, value);
		}
		catch(Exception argh) {
			throw new ReflectionException(argh);
		}
	}
	
	public static void setChar(Class clazz, Object object, String name, char value) throws ReflectionException {
		try {
			Field field = clazz.getDeclaredField(name);
			field.setAccessible(true);
			field.setChar(object, value);
		}
		catch(Exception argh) {
			throw new ReflectionException(argh);
		}
	}
	
	public static void setDouble(Class clazz, Object object, String name, double value) throws ReflectionException {
		try {
			Field field = clazz.getDeclaredField(name);
			field.setAccessible(true);
			field.setDouble(object, value);
		}
		catch(Exception argh) {
			throw new ReflectionException(argh);
		}
	}
	
	public static void setFloat(Class clazz, Object object, String name, float value) throws ReflectionException {
		try {
			Field field = clazz.getDeclaredField(name);
			field.setAccessible(true);
			field.setFloat(object, value);
		}
		catch(Exception argh) {
			throw new ReflectionException(argh);
		}
	}
	
	public static void setInt(Class clazz, Object object, String name, int value) throws ReflectionException {
		try {
			Field field = clazz.getDeclaredField(name);
			field.setAccessible(true);
			field.setInt(object, value);
		}
		catch(Exception argh) {
			throw new ReflectionException(argh);
		}
	}
	
	public static void setLong(Class clazz, Object object, String name, long value) throws ReflectionException {
		try {
			Field field = clazz.getDeclaredField(name);
			field.setAccessible(true);
			field.setLong(object, value);
		}
		catch(Exception argh) {
			throw new ReflectionException(argh);
		}
	}
	
	public static void setShort(Class clazz, Object object, String name, short value) throws ReflectionException {
		try {
			Field field = clazz.getDeclaredField(name);
			field.setAccessible(true);
			field.setShort(object, value);
		}
		catch(Exception argh) {
			throw new ReflectionException(argh);
		}
	}
	
}
