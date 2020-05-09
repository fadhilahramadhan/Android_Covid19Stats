/**
 * 
 */
package fadhilah.ramadhan.covid19stats.util.service;

/**
 * @author abiandono
 *
 */
public interface AsyncTaskCompleteListener<T> {
	public void onTaskComplete(T... params);
}
