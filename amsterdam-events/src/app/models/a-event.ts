export enum AEventStatus {
  DRAFT = 'DRAFT',
  PUBLISHED = 'PUBLISHED',
  CANCELED = 'CANCELED'
}

export class AEvent {
  constructor(
    public title: string,
    public status: AEventStatus,
    public start: Date,
    public isTicketed: boolean,
    public end: Date,
    public participationFee: number,
    public description: string,
    public maxParticipants: number) {
    console.log(JSON.stringify(this));
  }

  public equals(e): boolean {
    return this.title === e.titel &&
      this.description === e.description &&
      this.start === e.start &&
      this.end === e.end &&
      this.status === e.status &&
      this.maxParticipants === e.maxParticipants &&
      this.participationFee === e.participationFee;
  }
}
