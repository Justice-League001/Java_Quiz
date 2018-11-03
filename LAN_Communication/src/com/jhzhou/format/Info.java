package com.jhzhou.format;

import java.net.InetSocketAddress;

public class Info {
	public final InetSocketAddress target;
	public final int PID;
	public boolean STATE;
	public Object Att;
	public Info(InetSocketAddress target, int PID, boolean STATE, Object Att) {
		this.target = target;
		this.PID = PID;
		this.Att = Att;
		this.STATE = STATE;
	}
	public Info(InetSocketAddress target, int PID, boolean STATE) {
		this.target = target;
		this.PID = PID;
		this.STATE = STATE;
		this.Att = null;
	}
	
	
	/* £¨·Ç Javadoc£©
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Info [target=" + target + ", PID=" + PID + ", Msg=" + Att + "]";
	}
	/* £¨·Ç Javadoc£©
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + PID;
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		return result;
	}
	/* £¨·Ç Javadoc£©
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Info other = (Info) obj;
		if (PID != other.PID)
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		return true;
	}
}
