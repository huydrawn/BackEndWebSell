package spring.server.commercial.service.permission;

import java.util.Collection;
import java.util.Collections;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import spring.server.commercial.exception.permission.NotFoundObjIdentifyException;
import spring.server.commercial.exception.permission.PermissionException;
import spring.server.commercial.model.user.Admin;
import spring.server.commercial.security.AuthenticationJwtToken;
import spring.server.commercial.security.provider_auth.JwtAuthenticationProvider;
import spring.server.commercial.service.user.UserService;

@Service
@RequiredArgsConstructor
public class PermissionService {
	private final JdbcMutableAclService aclService;
	private final UserService userService;
	private final JwtAuthenticationProvider jwtAuthenticationProvider;

	@Transactional
	public void setOwner(ObjectIdentityImpl objectIdentityImpl) {

		MutableAcl acl = null;
		try {
			acl = (MutableAcl) aclService.readAclById(objectIdentityImpl);
		} catch (NotFoundException e) {
			acl = aclService.createAcl(objectIdentityImpl);
		}
		Sid sid = new PrincipalSid(getAuthentication());
		acl.setOwner(sid);
		aclService.updateAcl(acl);

	}

	public void setPermissionForUser(ObjectIdentityImpl objectIdentityImpl, Permission permission, boolean granting,
			int id) {
		String email = userService.findById(id).get().getEmail();
		if (checkOwner(objectIdentityImpl) || isAdmin()) {
			Authentication authentication = jwtAuthenticationProvider
					.authenticate(new AuthenticationJwtToken(email, null));
			authentication.setAuthenticated(true);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			MutableAcl acl = null;
			try {
				acl = (MutableAcl) aclService.readAclById(objectIdentityImpl);
			} catch (NotFoundException e) {
				acl = aclService.createAcl(objectIdentityImpl);
			}
			Sid sid = new PrincipalSid(authentication);
			acl.insertAce(acl.getEntries().size(), permission, sid, granting);
			aclService.updateAcl(acl);
		}
	}

	public boolean checkOwner(ObjectIdentityImpl objectIdentityImpl) {
		MutableAcl acl = null;
		try {
			acl = (MutableAcl) aclService.readAclById(objectIdentityImpl);
		} catch (NotFoundException e) {
			return false;
		}
		if (acl.getOwner().equals(new PrincipalSid(getAuthentication()))) {
			return true;
		}
		return false;
	}

	@Transactional
	public boolean checkPermission(ObjectIdentityImpl objectIdentityImpl, Permission permission)
			throws PermissionException {
		MutableAcl acl = null;
		if (!getAuthentication().isAuthenticated()) {
			throw new PermissionException("Haven't authenticate");
		}

		if (userService.findByEmail(getAuthentication().getPrincipal().toString()).get() instanceof Admin) {
			return true;
		}

		try {
			acl = (MutableAcl) aclService.readAclById(objectIdentityImpl);
		} catch (NotFoundException e) {
			throw new NotFoundObjIdentifyException(e.getMessage());
		}
		Sid sid = new PrincipalSid(getAuthentication());
		if (acl.getOwner().equals(sid)) {
			return true;
		}
		for (int i = acl.getEntries().size() - 1; i >= 0; i--) {
			AccessControlEntry ace = acl.getEntries().get(i);
			if (ace.getSid().equals(sid) && ace.getPermission().equals(permission)) {
				if (ace.isGranting()) {
					return true;
				} else {
					return false;
				}
			}
		}
		return true;
	}

	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	private boolean isAdmin() {
		if (userService.findByEmail(getAuthentication().getPrincipal().toString()).get() instanceof Admin)
			return true;
		else
			return false;
	}

	public void deleteObject(ObjectIdentityImpl objectIdentityImpl) {
		MutableAcl acl = null;
		try {
			acl = (MutableAcl) aclService.readAclById(objectIdentityImpl);

			aclService.deleteAcl(objectIdentityImpl, true);
		} catch (NotFoundException e) {

		}

	}
}
