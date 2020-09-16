export enum AEventStatus {
  DRAFT = 'DRAFT',
  PUBLISHED = 'PUBLISHED',
  CANCELED = 'CANCELED'
}

export class AEvent {
  id: number;
  title: string;
  start: Date;
  end: Date;
  description: string;
  status: AEventStatus;
  isTicketed: Boolean;
  participationFee: number;
  maxParticipants: number;
  private static eventid = 20001;


  constructor(id: number, title: string, start: Date, end: Date, description: string, status: AEventStatus, isTicketed: Boolean, participationFee: number, maxParticipants: number) {
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

// @ts-ignore
  public static createRandomAEvent(): AEvent {
    // @ts-ignore
    const ai = new AEvent();

    ai.id = this.eventid++;
    ai.title = 'The Fantastic Event-' + ai.id;

    ai.start = this.randomDate(new Date(2020, 9, 20), new Date(2020, 10, 29));
    ai.end = this.randomDate(ai.start, new Date(2021, 10, 30));

    ai.description = "example description";

    ai.status = this.getRandomStatue();

    ai.participationFee = Math.round(((Math.random() * 50) + Number.EPSILON) * 100) / 100;

    ai.isTicketed = Math.random() < 0.5;

    if (ai.isTicketed == false) {
      const fee = null;
      ai.participationFee = fee || 'free';
      ai.maxParticipants = null;
    } else {
      ai.maxParticipants = Math.floor((Math.random() + 1) * 6 * 100);
    }

    return ai;
  }

  private static randomDate(start: Date, end: Date) {
    return new Date(start.getTime() + Math.random() * (end.getTime() - start.getTime()));
  }

  private static getRandomStatue(): AEventStatus {
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
}
