package client.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import cache.AppDatabase;
import cache.ContactsDatabase;
import cache.LocationDatabase;
import cache.MessageDatabase;
import cache.SessionDatabase;
import cache.ProposalDatabase;
import cache.TripDatabase;
import cache.TripReactionDatabase;
import core.businessmessages.toServer.ParamsForAcceptTTRequest;
import core.businessmessages.toServer.ParamsForAddContacts;
import core.businessmessages.toServer.ParamsForAddLocation;
import core.businessmessages.toServer.ParamsForCancelTTRequest;
import core.businessmessages.toServer.ParamsForCreateUser;
import core.businessmessages.toServer.ParamsForGetChatMessages;
import core.businessmessages.toServer.ParamsForGetContacts;
import core.businessmessages.toServer.ParamsForGetFamilies;
import core.businessmessages.toServer.ParamsForGetFamilyMembers;
import core.businessmessages.toServer.ParamsForGetFamilyMessages;
import core.businessmessages.toServer.ParamsForGetLocations;
import core.businessmessages.toServer.ParamsForGetTripActivities;
import core.businessmessages.toServer.ParamsForGetTripReactions;
import core.businessmessages.toServer.ParamsForGetTrips;
import core.businessmessages.toServer.ParamsForGetUsers;
import core.businessmessages.toServer.ParamsForLogin;
import core.businessmessages.toServer.ParamsForNotifyCompleted;
import core.businessmessages.toServer.ParamsForRateFamilyMember;
import core.businessmessages.toServer.ParamsForRejectTTRequest;
import core.businessmessages.toServer.ParamsForGetTTRequests;
import core.businessmessages.toServer.ParamsForSendFamilyMessage;
import core.businessmessages.toServer.ParamsForSendTTProposal;
import core.businessmessages.toServer.ParamsForScheduleTrip;
import core.businessmessages.toServer.ParamsForSendTripReaction;
import core.businessmessages.toServer.ParamsForUpdateLocation;
import core.businessmessages.toServer.ParamsForUpdateLoginDetails;
import core.businessmessages.toServer.ParamsForUpdateUser;
import core.businessobjects.ChatMessage;
import core.businessobjects.Contact;
import core.businessobjects.FamilyMemberRating;
import core.businessobjects.FamilyMessage;
import core.businessobjects.Gender;
import core.businessobjects.Location;
import core.businessobjects.MatePurpose;
import core.businessobjects.NewsFeedItem;
import core.businessobjects.SessionDetails;
import core.businessobjects.FamilyMember;
import core.businessobjects.Family;
import core.businessobjects.Proposal;
import core.businessobjects.TargetGroup;
import core.businessobjects.TravelPurpose;
import core.businessobjects.Trip;
import core.businessobjects.TripActivity;
import core.businessobjects.TripReaction;
import core.businessobjects.User;
import core.businessobjects.UserLoginDetails;
import core.businessobjects.admin.PerformanceData;
import core.businessobjects.admin.ProgressData;
import libraries.JsonEncoder;
import cache.UserDatabase;
import cache.UserLoginDatabase;
import libraries.IsTrue;
import libraries.SecondsSinceEpoch;
import libraries.data.LocalDatabase;
import libraries.throwException;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import server.Enrich;
import server.StoredProcedures;
import server.admin.Top10Users;
import server.admin.performace_reports.PerformaceReportForToday;
import server.admin.performace_reports.PerformaceReportForYesterday;
import server.admin.progress_data.ProgressDataForAccountsCreated;
import server.admin.progress_data.ProgressDataForRidesShared;
import server.admin.progress_data.ProgressDataForRideShareProposalsApproved;
import server.admin.progress_data.ProgressDataForProposalsSent;
import server.admin.progress_data.ProgressDataForSearchesConducted;
import server.admin.progress_data.ProgressDataForTripsCompleted;
import server.backendobjects.BackEndTripFromParams;
import server.backendobjects.RespondToTrip;
import server.families.AddTripsCompleted;
import server.families.Families;
import server.families.GetFamilyMembers;
import server.families.MessageList;
import server.families.RateFamilyMember;
import server.families.TripIsCompletedByUser;
import server.trip.MarkTripMessagesAsSeen;
import server.trip.ResultForGetSearchAnalytics;
import server.trip.TripDecorator;
import server.users.BackEndUser;

public class HerbuyApi {


    public Call<List<Contact>> addContact(final ParamsForAddContacts paramsForAddContacts) {
        return new HerbuyCall<List<Contact>>() {
            @Override
            protected List<Contact> makeBody() {

                User currentUser = StoredProcedures.getCurrentUserOrThrowException(paramsForAddContacts.getSessionId());

                throwException.ifTrue(paramsForAddContacts.getToUserId().equalsIgnoreCase(paramsForAddContacts.getContactId()), "contact id and user id can not be the same");

                List<Contact> existingInstances = new ContactsDatabase().select(new LocalDatabase.Where<Contact>() {
                    @Override
                    public boolean isTrue(Contact contact) {
                        return IsTrue.forAll(
                                contact.isForUser(paramsForAddContacts.getToUserId()),
                                contact.isUser(paramsForAddContacts.getContactId())
                        );
                    }
                });


                if (existingInstances != null && existingInstances.size() > 0) {
                    throwException.saying("Contact already added");
                }

                //throwException.saying("working well");

                String uniqueId = UUID.randomUUID().toString();
                Contact contact = new Contact();
                contact.setUniqueId(uniqueId);
                contact.setTimestampLastModifiedInSecs(SecondsSinceEpoch.get());
                contact.setUserId(paramsForAddContacts.getToUserId());
                contact.setContactId(paramsForAddContacts.getContactId());

                new ContactsDatabase().save(uniqueId, contact);

                Enrich.contact(contact);
                return Collections.singletonList(contact);
            }
        };
    }

    public Call<List<TripReaction>> sendTripReaction(final ParamsForSendTripReaction params) {

        return new HerbuyCall<List<TripReaction>>() {
            @Override
            protected List<TripReaction> makeBody() {

                //determine who is sending the reaction
                User me = StoredProcedures.getCurrentUserOrThrowException(params.getSessionId());

                //check that i have not yet reacted

                //create the request
                String reactionId = UUID.randomUUID().toString();
                TripReaction currentUserReaction = new TripReaction();
                currentUserReaction.setReactionId(reactionId);
                currentUserReaction.setActorId(me.getUserId());
                currentUserReaction.setToTripId(params.getTripId());
                currentUserReaction.setReactionType(params.getReactionType());

                //throwException.saying("Sending requests not yet supported");
                validateBeforeSave(currentUserReaction);
                new TripReactionDatabase().save(reactionId, currentUserReaction);

                Enrich.messageToTripActor(currentUserReaction);
                return Collections.singletonList(currentUserReaction);
            }

            private void validateBeforeSave(TripReaction reaction) {

                throwException.ifNullOrEmpty(reaction.getReactionId(), "every reaction must have an ID");
                throwException.ifNullOrEmpty(reaction.getActorId(), "reaction missing actor info");
                throwException.ifNullOrEmpty(reaction.getToTripId(), "trip not specified for reaction");
                throwException.ifNullOrEmpty(reaction.getReactionType(), "reaction type not specified");
                throwException.ifNotInList(reaction.getReactionType(), TripReaction.Type.getList(), "Invalid reaction type");
            }

        };
    }

    public Call<List<Proposal>> sendProposal(final ParamsForSendTTProposal requestParameters) {
        return new HerbuyCall<List<Proposal>>() {
            @Override
            protected List<Proposal> makeBody() {
                return RespondToTrip.where(requestParameters);
            }

        };
    }

    public Call<List<Family>> getFamilies(final ParamsForGetFamilies params) {
        return new HerbuyCall<List<Family>>() {
            @Override
            protected List<Family> makeBody() {
                return Families.where(params);
            }
        };
    }

    public Call<List<FamilyMessage>> getFamilyMessages(final ParamsForGetFamilyMessages params) {
        return new HerbuyCall<List<FamilyMessage>>() {
            @Override
            protected List<FamilyMessage> makeBody() {
                final User currentUser = StoredProcedures.getCurrentUserOrThrowException(params.getSessionId());
                return MessageList.where(params, currentUser).getMessages();
            }
        };

    }

    public Call<List<FamilyMessage>> sendFamilyMessage(final ParamsForSendFamilyMessage params) {

        return new HerbuyCall<List<FamilyMessage>>() {
            @Override
            protected List<FamilyMessage> makeBody() {
                BackEndUser currentUser = BackEndUser.fromSessionId(params.getSessionId()); //User currentUser = StoredProcedures.getCurrentUserOrThrowException(params.getSessionId());
                throwException.ifNot(currentUser.isFound(), "Sender not found");
                throwException.ifNot(currentUser.isUser(params.getFromUserId()), "Not allowed"); //for now, can only send a message on your own behalf
                //ideally, u must be a member of the family to send a message
                FamilyMessage message = currentUser.sendMessage(params);
                Enrich.familyMessage(message);
                return Collections.singletonList(message);
            }

        };
    }

    public Call<List<Family>> notifyCompleted(final ParamsForNotifyCompleted paramsForNotifyCompleted) {
        return new HerbuyCall<List<Family>>() {
            @Override
            protected List<Family> makeBody() {

                User currentUser = StoredProcedures.getCurrentUserOrThrowException(paramsForNotifyCompleted.getSessionId());
                BackEndUser user2 = BackEndUser.fromUserId(currentUser.getUserId());

                if(!user2.isMemberOfFamily(paramsForNotifyCompleted.getFamilyId())){
                    throw new RuntimeException("You are not a traveller on this trip");

                }

                if(TripIsCompletedByUser.where(paramsForNotifyCompleted.getFamilyId(), user2.getUserId())){
                    throw new RuntimeException("Trip already indicated as completed");
                }

                AddTripsCompleted.where(paramsForNotifyCompleted.getFamilyId(), user2.getUserId());
                return Collections.singletonList(new Family());
            }
        };
    }

    public Call<List<FamilyMember>> getFamilyMembers(final ParamsForGetFamilyMembers params) {
        return new HerbuyCall<List<FamilyMember>>() {
            @Override
            protected List<FamilyMember> makeBody() {
                User currentUser = StoredProcedures.getCurrentUserOrThrowException(params.getSessionId());
                BackEndUser backEndUser = BackEndUser.fromSessionId(params.getSessionId());
                if(!backEndUser.isMemberOfFamily(params.getFamilyId())){
                    throw new RuntimeException("Permission denied");
                }
                return GetFamilyMembers.where(params.getFamilyId(), currentUser);

            }
        };
    }

    public Call<Map<String,Map<String,Integer>>> getTravelAnalytics() {

        return new HerbuyCall<Map<String, Map<String, Integer>>>() {

            @Override
            protected Map<String, Map<String, Integer>> makeBody() {
                ResultForGetSearchAnalytics searchAnalytics = new ResultForGetSearchAnalytics();
                Map<String,Map<String,Integer>> data = new HashMap<>();
                data.put("Preferred Travel Date",searchAnalytics.byPreferedTravelDate());
                data.put("Most Common Origins",searchAnalytics.mostCommonOrigins());
                data.put("Passengers Sought",searchAnalytics.passengerCountSought());
                data.put("Popular Travel Days",searchAnalytics.popularTravelDays());
                data.put("Popular Travel Times",searchAnalytics.popularTravelDayStages());
                data.put("Popular Travel Hours",searchAnalytics.popularTravelHours());
                data.put("Popular Travel Months",searchAnalytics.popularTravelMonths());
                data.put("Search Types",searchAnalytics.searchType());
                data.put("Specify Fuel Charge",searchAnalytics.specifyFuelCharge());
                data.put("Top Destinations",searchAnalytics.topDestinations());
                data.put("Travellers Per Ride",searchAnalytics.travellersPerRide());
                return  data;
            }
        };

    }

    public Call<Map<String,Map<String,Integer>>> getTop10Users() {

        return new HerbuyCall<Map<String, Map<String, Integer>>>() {

            @Override
            protected Map<String, Map<String, Integer>> makeBody() {
                Top10Users top10Users = new Top10Users();
                Map<String,Map<String,Integer>> data = new HashMap<>();
                data.put("By Searches conducted",top10Users.bySearchesConducted());
                data.put("By Rides Shared",top10Users.byRidesShared());
                return  data;
            }
        };

    }

    public Call<List<ProgressData>> getProgressReport() {


        return new HerbuyCall<List<ProgressData>>() {
            @Override
            protected List<ProgressData> makeBody() {

                List<ProgressData> progressDataSet = new ArrayList<>();
                progressDataSet.add(new ProgressDataForAccountsCreated());
                progressDataSet.add(new ProgressDataForSearchesConducted());
                progressDataSet.add(new ProgressDataForProposalsSent());
                progressDataSet.add(new ProgressDataForRideShareProposalsApproved());
                progressDataSet.add(new ProgressDataForRidesShared());
                progressDataSet.add(new ProgressDataForTripsCompleted());

                //accounts created
                return progressDataSet;

                //todo: other system analytics to consider
                //String[] progressIndicators = new String[]{"Accounts created","Accounts Blocked","Login Activities","Searches conducted","Travel Requests submitted","Matches made (Successful searches)","Unsucessful Searches","Rides Shared (Families)","Trips completed","Ratings Completed","Active Users","Revenue Collected","Expenditure"};

                /*

        List<ProgressData> progressDataSet = new ArrayList<>();
        for(int i = 0; i < progressIndicators.length; i++){
            ProgressData progressData = new ProgressData();
            progressData.setIndicatorDescription(progressIndicators[i]);
            progressDataSet.add(progressData);

            int overall = DummyData.randomInt(0,4000000);
            int thisMonth = (int) (0.1f * overall);
            int lastMonth = (int) (0.08f * overall);
            int today = thisMonth / 30;
            int yesterday = thisMonth / 40;

            progressData.setOverall(overall);
            progressData.setThisMonth(thisMonth);
            progressData.setLastMonth(lastMonth);
            progressData.setToday(today);
            progressData.setYesterday(yesterday);
        }

        return progressDataSet;*/

            }
        };



    }

    public Call<List<PerformanceData>> getPerformanceReport() {

        return new HerbuyCall<List<PerformanceData>>() {
            @Override
            protected List<PerformanceData> makeBody() {

                List<PerformanceData> performanceReports = new ArrayList<>();
                performanceReports.add(new PerformaceReportForToday());
                performanceReports.add(new PerformaceReportForYesterday());

                return performanceReports;
            }
        };

    }

    public Call<List<FamilyMemberRating>> rateFamilyMember(final ParamsForRateFamilyMember paramsForRateFamilyMember) {
        return new HerbuyCall<List<FamilyMemberRating>>() {
            @Override
            protected List<FamilyMemberRating> makeBody() {



                User currentUser = StoredProcedures.getCurrentUserOrThrowException(paramsForRateFamilyMember.getSessionId());

                return RateFamilyMember.where(
                        paramsForRateFamilyMember.getFamilyId(),
                        paramsForRateFamilyMember.getMemberIdToRate(),
                        currentUser.getUserId(),
                        paramsForRateFamilyMember.getRatingTimeKeeping(),
                        paramsForRateFamilyMember.getRatingFriendliness(),
                        paramsForRateFamilyMember.getRatingSafeDriving(),
                        paramsForRateFamilyMember.getRatingOtherFactors()
                );
            }
        };

    }


    public interface CodeBlock {
        void run();
    }

    public Call<List<Trip>> scheduleTrip(final ParamsForScheduleTrip paramsForScheduleTrip) {
        return new HerbuyCall<List<Trip>>() {
            @Override
            protected List<Trip> makeBody() {

                User currentUser = StoredProcedures.getCurrentUserOrThrowException(paramsForScheduleTrip.getSessionId());
                BackEndTripFromParams tripBeingScheduled = new BackEndTripFromParams(paramsForScheduleTrip, currentUser);
                Enrich.trip(tripBeingScheduled, currentUser);
                return Arrays.asList((Trip) tripBeingScheduled);
            }


        };
    }

    private static class Where {
        public static AppDatabase.Where<Contact> contactIsForUser(final String userId) {
            return new LocalDatabase.Where<Contact>() {
                @Override
                public boolean isTrue(Contact contact) {
                    return contact.isForUser(userId);
                }
            };
        }

        public static LocalDatabase.Where<Trip> alsoGoingToDestinationOfTrip(final Trip myTrip) {
            return new LocalDatabase.Where<Trip>() {
                @Override
                public boolean isTrue(Trip otherTrip) {
                    return
                            otherTrip.isFromSameOrigin(myTrip)
                                    && otherTrip.isToSameDestination(myTrip)
                                    && otherTrip.isOnSameDate(myTrip)
                                    && !otherTrip.isForSameUser(myTrip)
                            ;
                }
            };
        }
    }

    private static abstract class CriteriaForIncludingItem<ObjectType> implements LocalDatabase.Where<ObjectType> {

        @Override
        public boolean isTrue(ObjectType object) {
            beforeRunningTest(object);
            return amAllowedToSeeThisObject(object) && iWantToSeeThisObject(object);
        }

        protected abstract void beforeRunningTest(ObjectType object);

        protected abstract boolean amAllowedToSeeThisObject(ObjectType object);

        protected abstract boolean iWantToSeeThisObject(ObjectType object);

    }

    public Call<List<TripReaction>> getTripReactions(final ParamsForGetTripReactions parameters) {
        return new HerbuyCall<List<TripReaction>>() {
            @Override
            protected List<TripReaction> makeBody() {
                return new TripReactionDatabase().select(new CriteriaForIncludingItem<TripReaction>() {
                    @Override
                    protected void beforeRunningTest(TripReaction object) {
                        Enrich.messageToTripActor(object);
                        //throw new RuntimeException(JsonEncoder.encode(object));
                    }

                    @Override
                    protected boolean amAllowedToSeeThisObject(TripReaction object) {


                        User currentUser = StoredProcedures.getCurrentUserOrThrowException(parameters.getSessionId());
                        return IsTrue.forAll(
                                IsTrue.thatNotNull(object),
                                IsTrue.forAny(
                                        object.isSentByUser(currentUser.getUserId()),
                                        object.isSentToUser(currentUser.getUserId())

                                )
                        );
                    }

                    @Override
                    protected boolean iWantToSeeThisObject(TripReaction object) {
                        if (parameters == null) {
                            return true;
                        }
                        if (object == null) {
                            return false;
                        }

                        return IsTrue.forAll(
                                IsTrue.forAny(
                                        IsTrue.thatNullOrEmpty(parameters.getActorIdEquals()),
                                        object.isSentByUser(parameters.getActorIdEquals())

                                ),
                                IsTrue.forAny(
                                        IsTrue.thatNullOrEmpty(parameters.getTargetUserIdEquals()),
                                        object.isSentToUser(parameters.getTargetUserIdEquals())
                                ),
                                IsTrue.forAny(
                                        IsTrue.thatNullOrEmpty(parameters.getTripIdEquals()),
                                        object.isToTrip(parameters.getTripIdEquals())
                                )
                        );
                    }
                });

            }
        };
    }

    public Call<List<Proposal>> getTTRequests(final ParamsForGetTTRequests parameters) {
        return new HerbuyCall<List<Proposal>>() {
            @Override
            protected List<Proposal> makeBody() {
                return new ProposalDatabase().select(new CriteriaForIncludingItem<Proposal>() {
                    @Override
                    protected void beforeRunningTest(Proposal object) {
                        Enrich.messageToTripActor(object);
                        //throw new RuntimeException(JsonEncoder.encode(object));
                    }

                    @Override
                    protected boolean amAllowedToSeeThisObject(Proposal object) {
                        if (object.getProposalId().equalsIgnoreCase("")) {
                            return false;
                        }

                        User currentUser = StoredProcedures.getCurrentUserOrThrowException(parameters.getSessionId());
                        return IsTrue.forAll(
                                IsTrue.thatNotNull(object),
                                IsTrue.forAny(
                                        object.isSentByUser(currentUser.getUserId()),
                                        object.isSentToUser(currentUser.getUserId())

                                )
                        );
                    }

                    @Override
                    protected boolean iWantToSeeThisObject(Proposal object) {
                        if (parameters == null) {
                            return true;
                        }
                        if (object == null) {
                            return false;
                        }

                        return IsTrue.forAll(
                                IsTrue.forAny(
                                        IsTrue.thatNullOrEmpty(parameters.getActorIdEquals()),
                                        object.isSentByUser(parameters.getActorIdEquals())

                                ),
                                IsTrue.forAny(
                                        IsTrue.thatNullOrEmpty(parameters.getTargetUserIdEquals()),
                                        object.isSentToUser(parameters.getTargetUserIdEquals())
                                ),
                                IsTrue.forAny(
                                        IsTrue.thatNullOrEmpty(parameters.getTripIdEquals()),
                                        object.isToTrip(parameters.getTripIdEquals())
                                )
                        );
                    }
                });

            }
        };
    }

    public static class Clone {
        public static Proposal ttRequest(Proposal proposal) {
            return JsonEncoder.decode(
                    JsonEncoder.encode(proposal),
                    Proposal.class
            );
        }
    }

    public Call<List<Proposal>> cancelTTRequest(final ParamsForCancelTTRequest paramsForCancelTTRequest) {
        return new HerbuyCall<List<Proposal>>() {
            @Override
            protected List<Proposal> makeBody() {
                User currentUser = StoredProcedures.getCurrentUserOrThrowException(paramsForCancelTTRequest.getSessionId());
                //find the request

                Proposal proposal = findTheRequestOrDie(paramsForCancelTTRequest.getTtRequestId());
                //determine steps for allowing to cancel
                throwException.ifNull(proposal.getActorId(), "Request missing sender information");
                throwException.ifNot(proposal.isSentByUser(currentUser.getUserId()), "Only the person who sent this request can cancel it");

                throwException.ifTrue(proposal.isAccepted(), "Request already accepted");
                throwException.ifTrue(proposal.isRejected(), "Request already rejected");
                throwException.ifTrue(proposal.isExpired(), "Request already expired");
                throwException.ifTrue(proposal.isCancelled(), "Request already cancelled");


                //cancel the request and save
                proposal.setProposalStatus(Proposal.Status.CANCELLED);
                new ProposalDatabase().save(proposal.getProposalId(), proposal);

                //enrich the request and return it
                Enrich.messageToTripActor(proposal);
                return Arrays.asList(proposal);
            }


        };
    }

    private Proposal findTheRequestOrDie(String requestId) {
        return new ProposalDatabase().getByKeyOrDie(
                requestId,
                "Request does not exist"
        );
    }

    public Call<List<Proposal>> acceptTTRequest(final ParamsForAcceptTTRequest params) {
        return new HerbuyCall<List<Proposal>>() {
            @Override
            protected List<Proposal> makeBody() {
                User currentUser = StoredProcedures.getCurrentUserOrThrowException(params.getSessionId());
                //find the request

                Proposal proposal = findTheRequestOrDie(params.getTtRequestId());

                //we want to clone the request coz we dont want the encrihcment to affect the original request
                Proposal clone = Clone.ttRequest(proposal);
                Enrich.messageToTripActor(clone);

                throwException.ifNot(clone.isSentToUser(currentUser.getUserId()), "Only the person to whom this request was sent can accept it");
                throwException.ifTrue(clone.isAccepted(), "Request already accepted");
                throwException.ifTrue(clone.isRejected(), "Request already rejected");
                throwException.ifTrue(clone.isExpired(), "Request already expired");
                throwException.ifTrue(clone.isCancelled(), "Request already cancelled");


                //it is the original request we modify before save
                proposal.setProposalStatus(Proposal.Status.ACCEPTED);
                new ProposalDatabase().save(proposal.getProposalId(), proposal);

                //enrich the request and return it
                Enrich.messageToTripActor(proposal);
                return Arrays.asList(proposal);
            }
        };
    }

    public Call<List<Proposal>> rejectTTRequest(ParamsForRejectTTRequest paramsForCancelTTRequest) {
        return new HerbuyCall<List<Proposal>>() {
            @Override
            protected List<Proposal> makeBody() {
                return null;
            }
        };
    }

    public Call<List<ChatMessage>> getChatMessages(final ParamsForGetChatMessages paramsForGetChatMessages) {
        return new HerbuyCall<List<ChatMessage>>() {
            @Override
            protected List<ChatMessage> makeBody() {
                User currentUser = StoredProcedures.getCurrentUserOrThrowException(paramsForGetChatMessages.getSessionId());

                List<ChatMessage> userMessages = new MessageDatabase().select(new LocalDatabase.Where<ChatMessage>() {
                    @Override
                    public boolean isTrue(ChatMessage message) {
                        return message.isForUser(paramsForGetChatMessages.getForUserId())
                                && (
                                message.isFromUser(paramsForGetChatMessages.getContactId())
                                        || message.isToUser(paramsForGetChatMessages.getContactId())
                        );

                    }
                });
                return userMessages;
            }
        };
    }

    public Call<List<User>> getUsers(ParamsForGetUsers paramsForGetUsers) {
        return new HerbuyCall<List<User>>() {
            @Override
            protected List<User> makeBody() {
                return new UserDatabase().selectAll();
                /*
                List<User> userList = new ArrayList<>();
                for(int i = 0; i < 10; i++){
                    userList.add(DummyData.randomUser());
                }
                return userList;*/
            }
        };
    }

    private static class is {

        private static boolean Null(Object object) {
            return object == null;
        }

    }

    public Call<List<User>> createUser(final ParamsForCreateUser params) {

        return new HerbuyCall<List<User>>() {
            @Override
            protected List<User> makeBody() {

                throwException.ifTrue(params == null, "Sign up information not provided");

                //check that the telephone does not exist
                List<User> usersWithThisTelephone = new UserDatabase().select(new LocalDatabase.Where<User>() {
                    @Override
                    public boolean isTrue(User object) {
                        if (object == null) {
                            return false;
                        }


                        return stringEqualsIgnoreCase(object.getMobileNumber(), params.getMobileNumber());
                    }
                });
                throwException.ifTrue(usersWithThisTelephone.size() > 0, "Telephone already being used by someone else");

                //create a unique id to identify the user
                String userId = UUID.randomUUID().toString();

                //create user object
                User user = new User();

                user.setUserId(userId);
                user.setUserName(params.getUserName());
                user.setMobileNumber(params.getMobileNumber());
                user.setProfilePic("");
                user.setTimestampRegisteredInMillis(System.currentTimeMillis());

                //create login details for the user
                UserLoginDetails userLoginDetails = new UserLoginDetails();
                userLoginDetails.setUserId(userId);
                userLoginDetails.setMobileNumber(params.getMobileNumber());
                userLoginDetails.setPassword(params.getPassword());

                //before saving, check that the user mobile does not exist, else throw error

                //now, save the data
                new UserDatabase().save(userId, user);
                new UserLoginDatabase().save(userId, userLoginDetails);

                //get the just added user
                List<User> resultList = new ArrayList<>();
                resultList.add(new UserDatabase().getByKey(userId));
                return resultList;


            }
        };
    }

    public Call<List<User>> updateUser(final ParamsForUpdateUser params) {

        return new HerbuyCall<List<User>>() {
            @Override
            protected List<User> makeBody() {

                //create user object
                User user = new User();

                user.setUserId(params.getUserId());
                user.setUserName(params.getUserName());
                user.setMobileNumber(params.getMobileNumber());
                user.setProfilePic("");

                //replace existing object

                //get the just added user
                List<User> resultList = new ArrayList<>();
                resultList.add(new UserDatabase().save(params.getUserId(), user));
                return resultList;


            }
        };
    }

    public Call<List<TripActivity>> getTripActivities(ParamsForGetTripActivities paramsForGetTripActivities) {
        return new HerbuyCall<List<TripActivity>>() {
            @Override
            protected List<TripActivity> makeBody() {
                List<TripActivity> resultList = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    resultList.add(DummyData.randomTripActivity());
                }
                return resultList;
            }
        };
    }

    public Call<List<SessionDetails>> login(final ParamsForLogin paramsForLogin) {
        return new HerbuyCall<List<SessionDetails>>() {
            @Override
            protected List<SessionDetails> makeBody() {
                //get login details that match the received mobile and password
                List<UserLoginDetails> matchingLoginDetails = getMatchingLoginDetails(paramsForLogin);

                //if none, mean login failed
                throwException.ifTrue(matchingLoginDetails.size() < 1, "Invalid mobile number or password");

                //pick the first item in the array
                UserLoginDetails userLoginDetails = matchingLoginDetails.get(0);

                //determine the user Id and mobile of the user that just logged in
                String userIdLoggedIn = userLoginDetails.getUserId();
                String mobileNumberLoggedIn = userLoginDetails.getMobileNumber();

                //create a session for this user and store in the session database
                String sessionIdAssigned = UUID.randomUUID().toString();
                SessionDetails sessionDetails = new SessionDetails();

                sessionDetails.setSessionId(sessionIdAssigned);
                sessionDetails.setUserId(userIdLoggedIn);
                sessionDetails.setMobileNumber(mobileNumberLoggedIn);

                //save the session details
                new SessionDatabase().save(sessionIdAssigned, sessionDetails);

                Enrich.sessionDetails(sessionDetails);


                //then add a session to the session database
                return Collections.singletonList(sessionDetails);

            }
        };
    }

    private List<UserLoginDetails> getMatchingLoginDetails(final ParamsForLogin paramsForLogin) {
        return new UserLoginDatabase().select(new LocalDatabase.Where<UserLoginDetails>() {
            @Override
            public boolean isTrue(UserLoginDetails object) {
                //mobile number and password
                return
                        //-- user name and pass not empty objects to be compared
                        !stringEmpty(object.getMobileNumber())
                                &&
                                !stringEmpty(paramsForLogin.getMobileNumber())
                                &&
                                !stringEmpty(object.getPassword())
                                &&
                                !stringEmpty(paramsForLogin.getPassword())
                                &&

                                //----------------
                                stringEqualsIgnoreCase(object.getMobileNumber(), paramsForLogin.getMobileNumber())
                                && stringEqualsCaseSensitive(object.getPassword(), paramsForLogin.getPassword())
                        ;
            }
        });
    }

    public Call<List<UserLoginDetails>> getUserLoginDetails() {
        return new HerbuyCall<List<UserLoginDetails>>() {
            @Override
            protected List<UserLoginDetails> makeBody() {
                return new UserLoginDatabase().selectAll();
            }
        };
    }

    public Call<List<UserLoginDetails>> updateLoginDetails(final ParamsForUpdateLoginDetails paramsForUpdateLoginDetails) {
        return new HerbuyCall<List<UserLoginDetails>>() {
            @Override
            protected List<UserLoginDetails> makeBody() {
                UserLoginDetails userLoginDetails = new UserLoginDetails();
                userLoginDetails.setUserId(paramsForUpdateLoginDetails.getUserId());
                userLoginDetails.setMobileNumber(paramsForUpdateLoginDetails.getMobileNumber());
                userLoginDetails.setPassword(paramsForUpdateLoginDetails.getPassword());

                //check if user exists before update
                List<User> userList = new UserDatabase().select(new LocalDatabase.Where<User>() {
                    @Override
                    public boolean isTrue(User object) {
                        return stringEqualsCaseSensitive(object.getUserId(), paramsForUpdateLoginDetails.getUserId());
                    }
                });

                //if the the user does not exist, throw error
                if (userList.size() != 1) {
                    throw new RuntimeException("User does not exist");
                }

                //save the new user login details
                return Arrays.asList(new UserLoginDatabase().save(paramsForUpdateLoginDetails.getUserId(), userLoginDetails));

            }
        };
    }

    private abstract class HerbuyCall<T> implements Call<T> {

        @Override
        public Response<T> execute() throws IOException {
            return null;
        }


        @Override
        public boolean isExecuted() {
            return false;
        }

        @Override
        public void cancel() {

        }

        @Override
        public boolean isCanceled() {
            return false;
        }

        @Override
        public Call<T> clone() {
            return null;
        }

        @Override
        public Request request() {
            return null;
        }

        @Override
        public void enqueue(final Callback<T> callback) {
            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //callback.onResponse(HerbuyCall.this, Response.success(makeBody()));
                    try {
                        callback.onResponse(HerbuyCall.this, Response.success(makeBody()));
                    } catch (Throwable ex) {
                        callback.onFailure(HerbuyCall.this, ex);
                    }
                }

            }, 1000);

        }

        protected abstract T makeBody();

    }

    //=======================
    public Call<List<NewsFeedItem>> getStreamItems() {
        return new HerbuyCall<List<NewsFeedItem>>() {
            @Override
            protected List<NewsFeedItem> makeBody() {
                List<NewsFeedItem> stream = new ArrayList<>();
                //----------------------
                for (int i = 0; i < 10; i++) {
                    NewsFeedItem newsFeedItem = new NewsFeedItem();


                    stream.add(newsFeedItem);
                }
                return stream;
            }
        };
    }

    public Call<List<Trip>> getTrips(final ParamsForGetTrips params) {
        return new HerbuyCall<List<Trip>>() {
            @Override
            protected List<Trip> makeBody() {

                final User currentUser = StoredProcedures.getCurrentUserOrThrowException(params.getSessionId());

                MarkTripMessagesAsSeen.where(params.getTripIdMessagesSeen(), currentUser.getUserId());

                List<Trip> matchingTrips = new TripDatabase().select(new LocalDatabase.Where<Trip>() {
                    @Override
                    public boolean isTrue(Trip trip) {
                        Enrich.trip(trip, currentUser);
                        TripDecorator tripDecorator = JsonEncoder.decode(JsonEncoder.encode(trip), TripDecorator.class);

                        return IsTrue.forAll(
                                IsTrue.forAny(
                                        IsTrue.thatNullOrEmpty(params.getIsForUserId()),
                                        trip.isForUser(params.getIsForUserId())
                                ),
                                IsTrue.forAny(
                                        IsTrue.thatNullOrEmpty(params.getTripId()),
                                        trip.isTripId(params.getTripId())
                                ),
                                IsTrue.forAny(
                                        IsTrue.thatNullOrEmpty(params.getNotForUserId()),
                                        !trip.isForUser(params.getNotForUserId())
                                ),
                                IsTrue.forAny(
                                        IsTrue.thatNullOrEmpty(params.getAcceptingProposalsFromTripId()),
                                        tripDecorator.isAcceptingProposalsFromTrip(params.getAcceptingProposalsFromTripId())
                                )
                        );

                    }
                });

                //sort the list according to a prefered criteria
                if(matchingTrips != null){
                    Collections.sort(matchingTrips, new Comparator<Trip>() {
                        @Override
                        public int compare(Trip first, Trip second) {
                            if(first.isMoreRecentlyUpdatedThan(second)){
                                return -1;
                            }
                            else if(second.isMoreRecentlyUpdatedThan(first)){
                                return 1;
                            }
                            else{
                                return 0;
                            }
                        }
                    });
                }

                return matchingTrips;
            }
        };
    }

    public Call<List<Trip>> cancelTrip(String tripId) {
        return new HerbuyCall<List<Trip>>() {
            @Override
            protected List<Trip> makeBody() {
                List<Trip> tripList = new ArrayList<>();

                return tripList;
            }
        };
    }

    public Call<List<Location>> getLocations() {
        return new HerbuyCall<List<Location>>() {
            @Override
            protected List<Location> makeBody() {

                List<Location> results = new LocationDatabase().selectAll();
                sortByMostRecent(results);

                return results;

            }

            private void sortByMostRecent(List<Location> results) {
                Collections.sort(results, new Comparator<Location>() {
                    @Override
                    public int compare(Location lhs, Location rhs) {
                        if (lhs.getTimeStampLastModifiedInSeconds() > rhs.getTimeStampLastModifiedInSeconds()) {
                            return -1;
                        } else if (lhs.getTimeStampLastModifiedInSeconds() < rhs.getTimeStampLastModifiedInSeconds()) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                });
            }
        };
    }

    public Call<List<Contact>> getContacts(final ParamsForGetContacts paramsForGetContacts) {
        return new HerbuyCall<List<Contact>>() {
            @Override
            protected List<Contact> makeBody() {
                User currentUser = StoredProcedures.getCurrentUserOrThrowException(paramsForGetContacts.getSessionId());

                List<Contact> results = new ContactsDatabase().select(new LocalDatabase.Where<Contact>() {
                    @Override
                    public boolean isTrue(Contact contact) {
                        Enrich.contact(contact);
                        return contact.isForUser(paramsForGetContacts.getUserId());
                    }
                });
                sortByMostRecent(results);

                return results;

            }

            private void sortByMostRecent(List<Contact> results) {
                Collections.sort(results, new Comparator<Contact>() {
                    @Override
                    public int compare(Contact lhs, Contact rhs) {
                        if (lhs.getTimestampLastModifiedInSecs() > rhs.getTimestampLastModifiedInSecs()) {
                            return -1;
                        } else if (lhs.getTimestampLastModifiedInSecs() < rhs.getTimestampLastModifiedInSecs()) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                });
            }
        };
    }

    public Call<List<Location>> getLocations(final ParamsForGetLocations paramsForGetLocations) {
        return new HerbuyCall<List<Location>>() {
            @Override
            protected List<Location> makeBody() {

                return new LocationDatabase().select(new LocalDatabase.Where<Location>() {
                    @Override
                    public boolean isTrue(Location object) {
                        return
                                paramsForGetLocations == null
                                        ||
                                        (
                                                stringEmptyOrEquals(paramsForGetLocations.getLocationIdEquals(), object.getId())
                                        )
                                ;
                    }

                    private boolean stringEmptyOrEquals(String str1, String str2) {
                        return stringEmpty(str1) || stringEqualsIgnoreCase(str1, str2);
                    }
                });

            }
        };
    }

    private boolean stringEqualsIgnoreCase(String str1, String str2) {
        return str1.equalsIgnoreCase(str2);
    }

    private boolean stringEqualsCaseSensitive(String str1, String str2) {
        return str1.equals(str2);
    }

    private boolean stringEmpty(String str1) {
        return str1 == null || str1.trim().equalsIgnoreCase("");
    }

    public Call<List<Location>> updateLocation(final ParamsForUpdateLocation paramsForUpdateLocation) {
        return new HerbuyCall<List<Location>>() {
            @Override
            protected List<Location> makeBody() {
                //create the location object we want to persist from the data
                Location location = new Location();
                location.setId(paramsForUpdateLocation.getLocationId());
                location.setName(paramsForUpdateLocation.getName());

                //save that object in the place where the previos object existed
                new LocationDatabase().save(paramsForUpdateLocation.getLocationId(), location);


                //return information about the created/saved object
                return new ArrayList<Location>(Arrays.asList(new Location[]{
                        new LocationDatabase().getByKey(paramsForUpdateLocation.getLocationId())
                }));


            }
        };
    }

    public Call<List<Location>> addLocation(final ParamsForAddLocation paramsForAddLocation) {
        return new HerbuyCall<List<Location>>() {
            @Override
            protected List<Location> makeBody() {
                //create the location object we want to persist from the data
                Location location = new Location();
                String locationId = UUID.randomUUID().toString(); //id is just generated at time of adding
                location.setId(locationId);
                location.setName(paramsForAddLocation.getName()); //name of location comes from passed data
                location.setTimeStampLastModifiedInSeconds(SecondsSinceEpoch.get());
                //save that object
                new LocationDatabase().save(locationId, location);


                //return information about the created/saved object
                return new ArrayList<Location>(Arrays.asList(new Location[]{
                        new LocationDatabase().getByKey(locationId)
                }));


            }
        };
    }

    public Call<List<Gender>> getGenders() {

        return new HerbuyCall<List<Gender>>() {
            @Override
            protected List<Gender> makeBody() {
                String[] genderDescriptions = new String[]{"Male", "Female"};
                List<Gender> genderList = new ArrayList<>();
                for (String genderDesc : genderDescriptions) {
                    Gender gender = new Gender();
                    gender.setId(UUID.randomUUID().toString());
                    gender.setDescription(genderDesc);
                    genderList.add(gender);

                }
                return genderList;
            }
        };
    }


    public Call<List<TravelPurpose>> getTravelPurposes() {
        return new HerbuyCall<List<TravelPurpose>>() {
            @Override
            protected List<TravelPurpose> makeBody() {
                String[] purposes = new String[]{"Work", "Leisure", "Visit Loved ones", "Funeral", "Tour", "Other"};
                List<TravelPurpose> purposeList = new ArrayList<>();
                for (String purposeDesc : purposes) {
                    TravelPurpose item = new TravelPurpose();
                    item.setId(UUID.randomUUID().toString());
                    item.setDescription(purposeDesc);
                    purposeList.add(item);

                }
                return purposeList;
            }
        };
    }

    public Call<List<MatePurpose>> getMatePurposes() {
        return new HerbuyCall<List<MatePurpose>>() {
            @Override
            protected List<MatePurpose> makeBody() {
                String[] purposes = new String[]{"Share costs", "Meet new people", "Travel more comfortable", "Have group fun", "Other reason"};
                List<MatePurpose> purposeList = new ArrayList<>();
                for (String purposeDesc : purposes) {
                    MatePurpose item = new MatePurpose();
                    item.setId(UUID.randomUUID().toString());
                    item.setDescription(purposeDesc);
                    purposeList.add(item);

                }
                return purposeList;
            }
        };
    }

    public Call<List<TargetGroup>> getMyGroups() {
        return new HerbuyCall<List<TargetGroup>>() {
            @Override
            protected List<TargetGroup> makeBody() {
                String[] groups = new String[]{"Family", "Friends", "Work Mates"};
                List<TargetGroup> groupList = new ArrayList<>();
                for (String desc : groups) {
                    TargetGroup item = new TargetGroup();
                    item.setId(UUID.randomUUID().toString());
                    item.setDescription(desc);
                    groupList.add(item);

                }
                return groupList;
            }
        };
    }


}
