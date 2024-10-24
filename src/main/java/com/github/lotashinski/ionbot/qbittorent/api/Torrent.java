package com.github.lotashinski.ionbot.qbittorent.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Torrent {
	
	@JsonProperty("added_on")
	private long addedOn;

	@JsonProperty("amount_left")
	private long amountLeft;

	@JsonProperty("auto_tmm")
	private boolean autoTmm;

	@JsonProperty("availability")
	private long availability;

	@JsonProperty("category")
	private String category;

	@JsonProperty("comment")
	private String comment;

	@JsonProperty("completed")
	private long completed;

	@JsonProperty("completion_on")
	private long completionOn;

	@JsonProperty("content_path")
	private String contentPath;

	@JsonProperty("dl_limit")
	private long dlLimit;

	@JsonProperty("dlspeed")
	private long dlspeed;

	@JsonProperty("download_path")
	private String downloadPath;

	@JsonProperty("downloaded")
	private long downloaded;

	@JsonProperty("downloaded_session")
	private long downloadedSession;

	@JsonProperty("eta")
	private long eta;

	@JsonProperty("f_l_piece_prio")
	private boolean isFirstLastPieceArePrioritized;

	@JsonProperty("force_start")
	private boolean forceStart;

	@JsonProperty("has_metadata")
	private boolean hasMetadata;

	@JsonProperty("hash")
	private String hash;

	@JsonProperty("inactive_seeding_time_limit")
	private long inactiveSeedingTimeLimit;

	@JsonProperty("infohash_v1")
	private String infohashV1;

	@JsonProperty("infohash_v2")
	private String infohashV2;

	@JsonProperty("last_activity")
	private long lastActivity;

	@JsonProperty("magnet_uri")
	private String magnetUri;

	@JsonProperty("max_inactive_seeding_time")
	private long maxInactiveSeedingTime;

	@JsonProperty("max_ratio")
	private long maxRatio;

	@JsonProperty("max_seeding_time")
	private long maxSeedingTime;

	@JsonProperty("name")
	private String name;

	@JsonProperty("num_complete")
	private long numComplete;

	@JsonProperty("num_incomplete")
	private long numIncomplete;

	@JsonProperty("num_leechs")
	private long numLeechs;

	@JsonProperty("num_seeds")
	private long numSeeds;

	@JsonProperty("popularity")
	private long popularity;

	@JsonProperty("priority")
	private long priority;

	@JsonProperty("private")
	private boolean myprivate;

	@JsonProperty("progress")
	private double progress;

	@JsonProperty("ratio")
	private long ratio;

	@JsonProperty("ratio_limit")
	private long ratioLimit;

	@JsonProperty("reannounce")
	private long reannounce;

	@JsonProperty("root_path")
	private String rootPath;

	@JsonProperty("save_path")
	private String savePath;

	@JsonProperty("seeding_time")
	private long seedingTime;

	@JsonProperty("seeding_time_limit")
	private long seedingTimeLimit;

	@JsonProperty("seen_complete")
	private long seenComplete;

	@JsonProperty("seq_dl")
	private boolean isSequentialDownload;

	@JsonProperty("size")
	private long size;

	@JsonProperty("state")
	private String state;

	@JsonProperty("super_seeding")
	private boolean superSeeding;

	@JsonProperty("tags")
	private String tags;

	@JsonProperty("time_active")
	private long timeActive;

	@JsonProperty("total_size")
	private long totalSize;

	@JsonProperty("tracker")
	private String tracker;

	@JsonProperty("trackers_count")
	private long trackersCount;

	@JsonProperty("up_limit")
	private long upLimit;

	@JsonProperty("uploaded")
	private long uploaded;

	@JsonProperty("uploaded_session")
	private long uploadedSession;

	@JsonProperty("upspeed")
	private long upspeed;
	
}
