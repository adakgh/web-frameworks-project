export enum AEventStatus {
  DRAFT = 'DRAFT',
  PUBLISHED = 'PUBLISHED',
  CANCELED = 'CANCELED'
}

export class AEvent {

  constructor(id: number, title: string, start: Date, end: Date, description: string,
              // tslint:disable-next-line:ban-types
              status: AEventStatus, isTicketed: Boolean, participationFee: number, maxParticipants: number) {
    this.id = id;
    this.title = title;
    this.start = start;
    this.end = end;
    this.description = description;
    this.status = status;
    this.isTicketed = isTicketed;
    this.participationFee = participationFee;
    this.maxParticipants = maxParticipants;
  }

  public static eventid = 20001;
  id: number;
  title: string;
  start: Date;
  end: Date;
  description: string;
  status: AEventStatus;
  // tslint:disable-next-line:ban-types
  isTicketed: Boolean;
  participationFee: number;
  maxParticipants: number;

  public static createRandomAEvent(): AEvent {
    // @ts-ignore
    const ai = new AEvent();

    ai.id = this.eventid++;
    ai.title = 'The Fantastic Event-' + ai.id;

    ai.start = this.randomDate(new Date(2020, 9, 20), new Date(2020, 10, 29));
    ai.end = this.randomDate(ai.start, new Date(2021, 10, 30));

    ai.description = 'example description';

    ai.status = this.getRandomStatue();

    ai.participationFee = Math.round(((Math.random() * 50) + Number.EPSILON) * 100.0) / 100.0;

    ai.isTicketed = Math.random() < 0.5;

    if (ai.isTicketed === false) {
      const fee = null;
      ai.participationFee = fee || 0;
      ai.maxParticipants = null;
    } else {
      ai.maxParticipants = Math.floor((Math.random() + 1) * 6 * 100);
    }

    return ai;
  }

  // tslint:disable-next-line:typedef
  public static randomDate(start: Date, end: Date) {
    return new Date(start.getTime() + Math.random() * (end.getTime() - start.getTime()));
  }

  public static getRandomStatue(): AEventStatus {
    const key = Math.floor(Math.random() * Math.floor(3) + 1);
    if (key === 1) {
      return AEventStatus.PUBLISHED;
    }
    if (key === 2) {
      return AEventStatus.CANCELED;
    }
    if (key === 3) {
      return AEventStatus.DRAFT;
    } else {
      return null;
    }
  }

  // converting an AEvent object with data fields
  // only into a true AEvent instance that has been created with a constructor, and got its data
  public static trueCopy(aEvent: AEvent): AEvent {
    // @ts-ignore
    return (aEvent == null ? null : Object.assign(new AEvent(), aEvent));
  }

  public equals(e): boolean {
    return this.title === e.title &&
      this.description === e.description &&
      this.start === e.start &&
      this.end === e.end &&
      this.status === e.status &&
      this.maxParticipants === e.maxParticipants &&
      this.participationFee === e.participationFee;
  }
}
