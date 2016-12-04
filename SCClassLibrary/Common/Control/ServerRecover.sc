/*
ServerRecover.at(\key).value(s, { "YO!".postln }, { "boohoo.".postln });
*/

ServerRecover {
	classvar <bootStrategies;
	classvar <>default = \failAndAsk;

	*initClass {
		bootStrategies = (
			\reboot: { |server, onSuccess, onFailure|
				var successFunc = {
					"ServerRecover.at('reboot') worked for '%'.\n".postf(server);
					onSuccess.value(server);
					"removing again: ".post;
					ServerBoot.remove(successFunc, server.name).postln;
				};
				"Using ServerRecover.at('reboot') ...".postln;
				server.quit;
				// not working yet
				// ServerBoot.add(successFunc, server.name);
				server.boot;
			},

			\failAndAsk: { |server, onSuccess, onFailure|
				"// ServerRecover.at('failAndAsk') for server '%':\n".postf(server.name);
				"// There is already a server using port %. You can\n".postf(server.addr.port);
				"// * force-reboot the server:".postln;
				"ServerRecover.default = 'reboot'; %.boot; \n".postf(server.cs);
				"// * or choose a different port and boot: ".postln;
				"ServerRecover.default = 'useFreePort;' %.boot; \n".postf(server.cs);
			},

			\useFreePort: { |server, onSuccess, onFailure|
				var portIsFree = false, addr = server.addr;
				"// ServerRecover.at('useFreePort') for server '%':\n".postf(server.name);
				while { portIsFree.not } {
					addr.port = addr.port - 1;
					portIsFree = addr.portIsFree(post: false);
				};
				"// found free port to boot: %.\n".postf(addr.cs);
				server.boot;
			},
			\hijack: { |server, onSuccess, onFailure|
				var portIsFree = false, addr = server.addr;
				"// ServerRecover.at('hijack') for server '%':\n".postf(server.name);
				server.statusWatcher.serverRunning = true;
				// how to get pid then?
				defer ({ server.freeAll; }, 0.2);
			},
		)
	}

	*at { |key|
		^bootStrategies[key] ? bootStrategies[default];
	}
}