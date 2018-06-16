	package com.sensei.domain;

	import org.hibernate.annotations.Cache;
	import org.hibernate.annotations.CacheConcurrencyStrategy;

	import javax.persistence.*;
	import javax.validation.constraints.*;
	import java.io.Serializable;

	/**
	 * A Connections.
	 */
	@Entity
	@Table(name = "userconnections")
	//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	public class UserConnections implements Serializable {

	    private static final long serialVersionUID = 1L;

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @NotNull
	    @Column(name = "status", nullable = false)
	    private Integer status;

	    @ManyToOne
	    private User user;
	    
	    @OneToOne
	    private Items items;

		public UserConnections() {
		} 
	    
		public UserConnections(Long id, Integer status, User user, Items items) {
			super();
			this.id = id;
			this.status = status;
			this.user = user;
			this.items = items;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Integer getStatus() {
			return status;
		}

		public void setStatus(Integer status) {
			this.status = status;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public Items getItems() {
			return items;
		}

		public void setItems(Items items) {
			this.items = items;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((id == null) ? 0 : id.hashCode());
			result = prime * result + ((items == null) ? 0 : items.hashCode());
			result = prime * result + ((status == null) ? 0 : status.hashCode());
			result = prime * result + ((user == null) ? 0 : user.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			UserConnections other = (UserConnections) obj;
			if (id == null) {
				if (other.id != null)
					return false;
			} else if (!id.equals(other.id))
				return false;
			if (items == null) {
				if (other.items != null)
					return false;
			} else if (!items.equals(other.items))
				return false;
			if (status == null) {
				if (other.status != null)
					return false;
			} else if (!status.equals(other.status))
				return false;
			if (user == null) {
				if (other.user != null)
					return false;
			} else if (!user.equals(other.user))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "UserConnections [id=" + id + ", status=" + status + ", user=" + user + ", items=" + items + "]";
		}
		
		
	    
	    
	    
	    

	}
