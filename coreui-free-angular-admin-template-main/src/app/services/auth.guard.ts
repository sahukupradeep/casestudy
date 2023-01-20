import { Injectable } from "@angular/core";
import {
	ActivatedRouteSnapshot,
	CanActivate,
	Router,
	RouterStateSnapshot,
	UrlTree
} from "@angular/router";
import { AuthService } from "./auth.service";

@Injectable()
export class AuthGuard implements CanActivate {
	constructor(
		private authService: AuthService,
		private router: Router) { }
	canActivate(
		route: ActivatedRouteSnapshot,
		state: RouterStateSnapshot): boolean | Promise<boolean> {
		var isAuthenticated = this.authService.isAuthenticate;
		var isTemp= this.authService.isTemp;
		console.log(isTemp)
		if (!isAuthenticated) {
			this.router.navigate(['/login']);
		}
		if(isTemp){
			this.router.navigate(['/change-password']);
		}
		return isAuthenticated;
	}
}
