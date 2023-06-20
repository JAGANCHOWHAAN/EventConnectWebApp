/*
 * Use App.getDependency for Dependency Injection
 * eg: var DialogService = App.getDependency('DialogService');
 */

/* perform any action on widgets/variables within this block */
Page.onReady = function() {
    Page.urlTextChange = function($event, widget, newVal, oldVal) {
        var baseUrl = "https://www.wavemakeronline.com/run-j826pwx24j/EventConnectRough_master/#/EventDetails?eventID=5";
        var eventId = Page.Variables.svEventDetails.id;
        console.log("eventId", eventId);
        var eventUrl = baseUrl + "/events/" + eventId;
        Page.Widgets.urlText.value = eventUrl;
    };
    Page.copyUrl = function() {
        var urlText = Page.Widgets.urlText.datavalue;
        urlText.select();
        document.execCommand("copy");
    };
};

Page.svInsertAttendeeDetailonSuccess = function(variable, data) {
    var userId = Page.Widgets.UserId.datavalue;
    console.log(UserId);
    var eventId = Page.Widgets.EventId.datavalue;
    console.log(eventId);
    var registerQuery = "SELECT * FROM attendee WHERE User_Id = " + userId + " AND Event_Id = " + eventId + " AND status = 'registered'";
    var registerResult = wm.database.query({
        sql: registerQuery,
        onSuccess: function(data) {
            if (data.length > 0) {
                Page.Widgets.Register.caption = "Registered";
                Page.Widgets.Register.disabled = true;
            }
        }
    });

};