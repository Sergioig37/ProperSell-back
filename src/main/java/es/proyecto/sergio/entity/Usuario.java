package es.proyecto.sergio.entity;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity

@Getter
@Setter
@Table(name = "Usuario", uniqueConstraints = {@UniqueConstraint(columnNames = {"correo, username"})})
public class Usuario implements UserDetails{

	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idUsuario")
	private Long id;


	private String username;

	private String password;

	private String correo;

	private String nombreReal;

	private boolean habilitado;

	private String numeroTelefono;
	@Enumerated(EnumType.STRING)
	private Rol role;

	@OneToMany(mappedBy="propietario", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Fetch(value = FetchMode.SUBSELECT)
	@JsonManagedReference
	private List<Propiedad> propiedades;

	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.DETACH)
	@JoinTable(name = "alerta_cliente",
		joinColumns = @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario"),
		inverseJoinColumns = @JoinColumn(name = "idAlerta", referencedColumnName = "idAlerta")
	)
	@JsonManagedReference
	private Set<Alerta> alertas;

	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return List.of(new SimpleGrantedAuthority((role.name())));
	}
	@Override
	public String getUsername() {

		return username;
	}
	@Override
	public boolean isAccountNonExpired() {

		return true;
	}
	@Override
	public boolean isAccountNonLocked() {

		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}
	@Override
	public boolean isEnabled() {

		if(habilitado==true){
			return true;
		}
		else{
			return false;
		}


	}

	@Override
	public String toString() {
		return "Usuario{" +
				"id=" + id +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", correo='" + correo + '\'' +
				", nombreReal='" + nombreReal + '\'' +
				", habilitado=" + habilitado +
				", numeroTelefono='" + numeroTelefono + '\'' +
				", role=" + role +
				", propiedades=" + propiedades +
				", alertas=" + alertas +
				'}';
	}
}