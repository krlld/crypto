import { Authority } from './Authority';

export interface Role {
	id?: number;
	name: string;
	description: string;
	authorityIds?: number[];
	authorities?: Authority[];
}
