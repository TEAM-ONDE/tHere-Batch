package onde.there_batch.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Collection;
import java.util.HashSet;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import onde.there_batch.dto.MemberDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member implements UserDetails {

	@Id
	@Column(name = "member_id")
	private String id;
	private String email;
	private String password;
	private String name;
	private String nickName;
	private String profileImageUrl;

	public Member(String id, String email, String password, String name, String nickName) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.nickName = nickName;
	}

	public Member(String id, String email, String password, String name) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
	}

	public static Member from(MemberDto.SignupRequest request, String encodedPassword) {
		Member member = new Member();
		member.id = request.getId();
		member.email = request.getEmail();
		member.name = request.getName();
		member.nickName = request.getNickName();
		member.password = encodedPassword;
		return member;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new HashSet<>(); // 이번 프로젝트에서는 ROLE에 따른 분리를 진행하지는 않는다.
	}

	@Override
	public String getUsername() {
		return id;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true; //계정이 만료가 안되었는지
	}

	@Override
	public boolean isAccountNonLocked() {
		return true; //계정이 잠기지 않았는지
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true; //계정 패스워드가 만료 안되었는지
	}

	@Override
	public boolean isEnabled() {
		return true; //계정이 사용 가능한가
	}

	public void update(MemberDto.UpdateRequest updateRequest, String encodedPassword,
		String profileImageUrl) {
		this.email = updateRequest.getEmail();
		this.name = updateRequest.getName();
		this.password = encodedPassword;
		this.profileImageUrl = profileImageUrl;
		this.nickName = updateRequest.getNickName();
	}
}
