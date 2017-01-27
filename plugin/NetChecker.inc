//Add CreateNetChecker method to global app object. 
app.CreateNetChecker = function() { return new NetChecker();}

//Plugin wrapper class.
function NetChecker( options )
{
    this.plg = _CreatePlugin( "com.sandudhate.plugins.user.NetChecker", options );  
	this.GetVersion = function( num, txt ) { return parseFloat( this.plg.Send( "GetVersion" ) ); }
    this.MyFunc = function( txt, num, bool ) { this.plg.Send( "MyFunc", txt, num, bool ); }
    this.SetOnMyReply = function( cb ) { this.plg.Send( "SetOnMyReply", cb.name ); }
    this.SaveMyImage = function( img ) { this.plg.SendImg( "SaveMyImage", img ); }
    this.GetConnectivityStatus = function() { return this.plg.Send( "GetConnectivityStatusString" ); }
}
