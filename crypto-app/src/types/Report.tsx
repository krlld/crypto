export interface Report {
	id?: number;
	title: string;
	description: string;
	sourceFileId: string;
	resultFileId?: string;
	userId?: number;
	isPublic: boolean;
	createdAtDate?: string;
}
