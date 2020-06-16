package dao;

import java.io.Serializable;
import java.util.List;

import bd.domains.Key;
import bd.services.KeyService;

public class KeyDAO implements Serializable{

	private static final long serialVersionUID = 2997203263809470796L;
	private List<Key> keyList;
	private KeyService ks;
	
	public KeyDAO(){}
	
	/*public List<Key> getKeyList() {
		return keyList;
	}

	public void setKeyList(List<Key> keyList) {
		this.keyList = keyList;
	}

	public List<Key> getOperKeys(String opername) {
		ks = new KeyService();
		keyList = ks.getOperKeys(opername);
		ks = null;
		return keyList;
	}

	public List<Key> getLazyKeyList(int first, int pageSize, String opername) 
	{
		ks = new KeyService();
		keyList = ks.getOperKeysLazy(first,pageSize,opername);
		ks = null;
		return keyList;
	}

	public int countPlayersTotal(String opername) 
	{
		ks = new KeyService();
		Integer rowCount = ks.getRowCount(opername);
		ks = null;
		return rowCount;
	}

	public List<Key> getFilteredLazyKeys(int first, int pageSize, String opername, String filterValue) 
	{
		ks = new KeyService();
		keyList = ks.getFilteredKeys(first, pageSize, opername, filterValue);
		ks = null;
		return keyList;
	}

	public int getFilteredRowCount(String opername, String filterValue) 
	{
		ks = new KeyService();
		Integer rowCount = ks.getFilteredRowCount(opername,filterValue);
		ks = null;
		return rowCount;
	}
*/
}
