/*
ServerRecover.at(\key).value(s, { "YO!".postln }, { "boohoo.".postln });
*/

ServerRecover {
	classvar <bootStrategies;
	classvar <>default = \failAndAsk;

	*findProcesses {
		var candidates = unixCmdGetStdOut("ps aux | grep %".format(Server.program.basename));
		var filtered = candidates.split($\n).reject(_.contains("grep")).reject(_.isEmpty);
		var dicts = filtered.collect { |line|
			var items = line.split($ ).reject(_.isEmpty);
			var pidStr = items[1];
			var pid = pidStr !? { pidStr.interpret };
			var portIndex = items.indexOfEqual("-u");
			var port = portIndex !? { items[portIndex + 1].interpret };
			(username: items[0], pid: pid, port: port);
		};
		^dicts
	}

	*pidForPort { |port=57110|
		var foundDict = ServerRecover.findProcesses.detect { |dict| dict.port == port };
		^foundDict !? { foundDict.pid };
	}

	*initClass {
		bootStrategies = (
			\failAndAsk: { |server, onSuccess, onFailure|
				"// ServerRecover 'failAndAsk' for server '%' - options:\n".postf(server.name);
				"// There is already a server using port %. You can\n".postf(server.addr.port);
				"// * force-reboot the server:".postln;
				"ServerRecover.default = 'reboot'; %.boot; \n".postf(server.cs);
				"// * or find a different port and boot: ".postln;
				"ServerRecover.default = 'useFreePort;' %.boot; \n".postf(server.cs);
				"// * or hijack the existing server: ".postln;
				"ServerRecover.default = 'hijack;' %.boot; \n".postf(server.cs);
			},

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

			\useFreePort: { |server, onSuccess, onFailure|
				var portIsFree = false, addr = server.addr;
				while { portIsFree.not } {
					addr.port = addr.port - 1;
					portIsFree = addr.portIsFree(post: false);
				};
				server.boot;
				"// ServerRecover 'useFreePort' % for server '%':\n".postf(addr.cs, server.name);
			},

			\hijack: { |server, onSuccess, onFailure|
				var foundPid = ServerRecover.pidForPort(server.addr.port).postln;
				"// ServerRecover 'hijack' pid % for server '%'.\n".postf(foundPid, server.name);
				foundPid !? { server.prSetPid(foundPid) };
				server.startAliveThread;
				defer ({ server.freeAll; }, 0.1);
			},
		)
	}

	*at { |key|
		^bootStrategies[key] ? bootStrategies[default];
	}
}