//Add CreateNetChecker method to global app object. 
app.CreateNetChecker = function() { return new NetChecker();}

//Plugin wrapper class.
function NetChecker( options )
{
    this.plg = _CreatePlugin( "com.sandudhate.plugins.user.NetChecker", options );  
	
	this.GetVersion = function() { return parseFloat( this.plg.Send( "GetVersion" ) ); }
    
    this.IsConnected = function () {
      vvisconn = this.plg.Send("IsConnected");
      if(vvisconn === "Yes") return true;
      else return false;
    }
    
    this.GetType = function() {
     return this.plg.Send("GetType");
    }
    
    this.GetSubType = function() {
     return this.plg.Send("GetSubType");
    }
     
    this.GetExtras = function() {
      return this.plg.Send("GetExtras");
    }
     
    this.GetReason = function() {
      return this.plg.Send("GetReason");
    }
    
    this.GetState = function() {
      return this.plg.Send("GetState");
    }

    this.GetDetailedState = function() {
      return this.plg.Send("GetDetailedState");
    }
    
    this.IsRoaming = function() {
      visrmng = this.plg.Send("IsRoaming");
      if(visrmng === "Yes") return true;
      else if(visrmng == "No") return false;
    }

}
