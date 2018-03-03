package interfaces;

import pojo.Website;

public interface Editable {

	void add(Website website);
	void update(Website website);
	void delete(Website website);
}
