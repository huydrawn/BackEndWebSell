package spring.server.commercial.service.permission;

import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import spring.server.commercial.exception.permission.NotFoundObjIdentifyException;
import spring.server.commercial.exception.permission.NotOwnerException;
import spring.server.commercial.exception.permission.PermissionException;
import spring.server.commercial.model.user.Admin;
import spring.server.commercial.security.JwtTokenAuthentication;
import spring.server.commercial.security.provider_auth.JwtAuthenticationProvider;
import spring.server.commercial.service.user.UserService;

@Service
@RequiredArgsConstructor
public class PermissionService {
	private final JdbcMutableAclService aclService;
	private final UserService userService;
	private final JwtAuthenticationProvider jwtAuthenticationProvider;

	
	public void setOwner(ObjectIdentityImpl objectIdentityImpl) {

		MutableAcl acl = null;
		try {
			acl = (MutableAcl) aclService.readAclById(objectIdentityImpl);
		} catch (NotFoundException e) {
			acl = aclService.createAcl(objectIdentityImpl);
			
		}
		Sid sid = new PrincipalSid(getAuthentication());
		acl.setOwner(sid);
		acl.insertAce(acl.getEntries().size(), BasePermission.ADMINISTRATION , sid ,true );
		aclService.updateAcl(acl);

	}

	public void setPermissionForUser(ObjectIdentityImpl objectIdentityImpl, Permission permission, boolean granting,
			int id) {
		String email = userService.findById(id).get().getEmail();
		if (checkOwner(objectIdentityImpl) || isAdmin()) {
			Authentication authentication = jwtAuthenticationProvider
					.authenticate(new JwtTokenAuthentication(email, null));
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

	public boolean isOwner(ObjectIdentityImpl objectIdentityImpl) throws NotOwnerException {
		if (this.checkOwner(objectIdentityImpl)) {
			return true;
		} else {
			throw new NotOwnerException("This user not have owner of this object");
		}
	}

	@Transactional
	public boolean checkPermission(ObjectIdentityImpl objectIdentityImpl, Permission permission)
			throws PermissionException { 
		MutableAcl acl = null;
		if (!getAuthentication().isAuthenticated()) {
			throw new PermissionException("Haven't authenticate");
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
