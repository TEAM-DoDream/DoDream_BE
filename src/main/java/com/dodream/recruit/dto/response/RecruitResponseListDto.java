package com.dodream.recruit.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record RecruitResponseListDto(
        @JsonProperty("jobs")
        Jobs jobs
) {
    public record Jobs(
            @JsonProperty("count")
            int count,

            @JsonProperty("start")
            int start,

            @JsonProperty("total")
            String total,

            @JsonProperty("job")
            List<Job> job
    ){
        public record Job(

            @JsonProperty("url")
            String url,

            @JsonProperty("active")
            int active,

            @JsonProperty("company")
            Company company,

            @JsonProperty("position")
            Position position,

            @JsonProperty("keyword")
            String keyword,

            @JsonProperty("salary")
            Salary salary,

            @JsonProperty("id")
            String id,

            @JsonProperty("posting-timestamp")
            String postingTimestamp,

            @JsonProperty("posting-date")
            String postingDate,

            @JsonProperty("modification-timestamp")
            String modificationTimestamp,

            @JsonProperty("opening-timestamp")
            String openingTimestamp,

            @JsonProperty("expiration-timestamp")
            String expirationTimestamp,

            @JsonProperty("expiration-date")
            String expirationDate,

            @JsonProperty("close-type")
            CloseType closeType,

            @JsonProperty("read-cnt")
            String readCnt,

            @JsonProperty("apply-cnt")
            String applyCnt
        ) {
            public record Company(

                    @JsonProperty("detail")
                    Detail detail
            ) {
                public record Detail(

                        @JsonProperty("href")
                        String href,

                        @JsonProperty("name")
                        String name
                ) {}
            }

            public record Position(

                @JsonProperty("title")
                String title,

                @JsonProperty("industry")
                Industry industry,

                @JsonProperty("location")
                Location location,

                @JsonProperty("job-type")
                JobType jobType,

                @JsonProperty("job-mid-code")
                JobMidCode jobMidCode,

                @JsonProperty("job-code")
                JobCode jobCode,

                @JsonProperty("experience-level")
                ExperienceLevel experienceLevel,

                @JsonProperty("required-education-level")
                RequiredEducationLevel requiredEducationLevel
            ) {
                public record Industry(

                        @JsonProperty("code")
                        String code,

                        @JsonProperty("name")
                        String name
                ) {}

                public record Location(

                        @JsonProperty("code")
                        String code,

                        @JsonProperty("name")
                        String name
                ) {}

                public record JobType(

                        @JsonProperty("code")
                        String code,

                        @JsonProperty("name")
                        String name
                ) {}

                public record JobMidCode(

                        @JsonProperty("code")
                        String code,

                        @JsonProperty("name")
                        String name
                ) {}

                public record JobCode(

                        @JsonProperty("code")
                        String code,

                        @JsonProperty("name")
                        String name
                ) {}

                public record ExperienceLevel(

                        @JsonProperty("code")
                        int code,

                        @JsonProperty("min")
                        int min,

                        @JsonProperty("max")
                        int max,

                        @JsonProperty("name")
                        String name
                ) {}

                public record RequiredEducationLevel(

                        @JsonProperty("code")
                        String code,

                        @JsonProperty("name")
                        String name
                ) {}
            }

            public record Salary(

                    @JsonProperty("code")
                    String code,

                    @JsonProperty("name")
                    String name
            ) {}

            public record CloseType(

                    @JsonProperty("code")
                    String code,

                    @JsonProperty("name")
                    String name
            ) {}
        }
    }
}


