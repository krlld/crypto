import { Role } from './Role';

export interface User {
	id?: number;
	email: string;
	password: string;
	name: string;
	lastname: string;
	avatarId: string | null;
	roleIds?: number[];
	roles?: Role[];
}
