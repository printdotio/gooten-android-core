package com.gooten.core.types;

/**
 * Utility class for holding value locally which could be changed from inner
 * scopes. </br>
 * </br>
 * <b>Example usage:</b>
 * 
 * <pre>
 * <code>
 * final Local&ltBoolean&gt b = new Local&ltBoolean&gt();
 * final CountDownLatch l = new CountDownLatch(1);
 * 
 * new Thread() {
 *   public void run() {
 *     b.val = true;
 *     l.countDown();
 *   }
 * }.start();
 * 
 * l.await(); // await for other thread to assign value
 * System.out.println(b.val);
 * </code>
 * </pre>
 * 
 * @author Vlado
 * 
 * @param <T>
 *            The type of value held by {@code Local} object
 */
public class Local<T> {

	public T val;

	/**
	 * Constructs new {@code Local} with {@code null} value.
	 */
	public Local() {}

	/**
	 * Constructs new {@code Local} holding supplied value.
	 * 
	 * @param val
	 *            The value to be assigned.
	 */
	public Local(T val) {
		this.val = val;
	}

}
